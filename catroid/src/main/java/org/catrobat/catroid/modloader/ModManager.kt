package org.catrobat.catroid.modloader

import android.content.Context
import android.util.Log
import org.catrobat.catroid.utils.ToastUtil
import org.json.JSONObject
import org.json.JSONArray
import java.io.File
import org.luaj.vm2.Globals
import org.luaj.vm2.lib.jse.JsePlatform
import org.luaj.vm2.LuaValue
import org.luaj.vm2.lib.TwoArgFunction

object ModManager {
    private const val MODS_DIR = "mods"
    private val activeMods = mutableListOf<ModData>()
    private lateinit var globals: Globals

    data class ModData(val id: String, val name: String, val path: String, val categories: List<CustomCategory>)
    data class CustomCategory(val name: String, val color: String)

    fun init(context: Context) {
        globals = JsePlatform.standardGlobals()
        setupLuaBridge(context)
        loadActiveMods(context)
    }

    private fun setupLuaBridge(context: Context) {
        val catroidLib = LuaValue.tableOf()
        
        catroidLib.set("toast", object : TwoArgFunction() {
            override fun call(arg1: LuaValue, arg2: LuaValue): LuaValue {
                ToastUtil.showSuccess(context, arg2.tojstring())
                return LuaValue.NIL
            }
        })

        catroidLib.set("log", object : TwoArgFunction() {
            override fun call(arg1: LuaValue, arg2: LuaValue): LuaValue {
                Log.d("ModLua", arg2.tojstring())
                return LuaValue.NIL
            }
        })

        globals.set("catroid", catroidLib)
    }

    private fun loadActiveMods(context: Context) {
        activeMods.clear()
        val modDir = File(context.filesDir, MODS_DIR)
        if (!modDir.exists()) modDir.mkdirs()
        
        modDir.listFiles()?.forEach { file ->
            if (file.isDirectory) {
                val infoFile = File(file, "mod_info.json")
                val categoriesFile = File(file, "categories.json")
                val luaFile = File(file, "main.lua")
                
                var name = file.name
                val customCats = mutableListOf<CustomCategory>()

                if (infoFile.exists()) {
                    try {
                        val json = JSONObject(infoFile.readText())
                        name = json.optString("name", name)
                    } catch (e: Exception) { }
                }

                if (categoriesFile.exists()) {
                    try {
                        val catsArray = JSONArray(categoriesFile.readText())
                        for (i in 0 until catsArray.length()) {
                            val catJson = catsArray.getJSONObject(i)
                            customCats.add(CustomCategory(catJson.getString("name"), catJson.getString("color")))
                        }
                    } catch (e: Exception) { }
                }

                activeMods.add(ModData(file.name, name, file.absolutePath, customCats))

                if (luaFile.exists()) {
                    try {
                        val chunk = globals.loadfile(luaFile.absolutePath)
                        chunk.call()
                    } catch (e: Exception) {
                        Log.e("ModManager", "Error running Lua script for mod $name: ${e.message}")
                    }
                }
            }
        }
    }

    fun getOverrideFile(resourceName: String): File? {
        activeMods.forEach { mod ->
            val override = File(mod.path, resourceName)
            if (override.exists()) return override
        }
        return null
    }

    fun getCustomCategories() = activeMods.flatMap { it.categories }

    fun deleteMod(context: Context, modId: String): Boolean {
        val modDir = File(File(context.filesDir, MODS_DIR), modId)
        val deleted = modDir.deleteRecursively()
        if (deleted) loadActiveMods(context)
        return deleted
    }
}
