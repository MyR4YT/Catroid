package com.myradev.lunarcode.modloader

import android.content.Context
import android.util.Log
import com.myradev.lunarcode.utils.ToastUtil
import org.json.JSONObject
import org.json.JSONArray
import java.io.File
import org.luaj.vm2.Globals
import org.luaj.vm2.lib.jse.JsePlatform
import org.luaj.vm2.LuaValue
import org.luaj.vm2.lib.TwoArgFunction
import org.luaj.vm2.lib.OneArgFunction

object ModManager {
    private const val MODS_DIR = "mods"
    private val activeMods = mutableListOf<ModData>()
    private lateinit var globals: Globals
    private val customBricks = mutableListOf<CustomBrickData>()

    data class ModData(val id: String, val name: String, val path: String, val categories: List<CustomCategory>)
    data class CustomCategory(val name: String, val color: String)
    data class CustomBrickData(val name: String, val category: String, val luaCallback: LuaValue)

    fun init(context: Context) {
        globals = JsePlatform.standardGlobals()
        setupLuaBridge(context)
        loadActiveMods(context)
    }

    private fun setupLuaBridge(context: Context) {
        val catroidLib = LuaValue.tableOf()
        
        catroidLib.set("register_brick", object : TwoArgFunction() {
            override fun call(brickName: LuaValue, category: LuaValue): LuaValue {
                // Placeholder for future callback registration
                Log.d("ModLua", "Registered custom brick: ${brickName.tojstring()} in ${category.tojstring()}")
                return LuaValue.NIL
            }
        })

        catroidLib.set("toast", object : OneArgFunction() {
            override fun call(msg: LuaValue): LuaValue {
                ToastUtil.showSuccess(context, msg.tojstring())
                return LuaValue.NIL
            }
        })

        globals.set("lunar", catroidLib)
    }

    private fun loadActiveMods(context: Context) {
        activeMods.clear()
        val modDir = File(context.filesDir, MODS_DIR)
        if (!modDir.exists()) modDir.mkdirs()
        
        modDir.listFiles()?.forEach { file ->
            if (file.isDirectory) {
                val luaFile = File(file, "main.lua")
                if (luaFile.exists()) {
                    try {
                        globals.loadfile(luaFile.absolutePath).call()
                    } catch (e: Exception) { Log.e("ModManager", "Lua Error: ${e.message}") }
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

    fun deleteMod(context: Context, modId: String): Boolean {
        val modDir = File(File(context.filesDir, MODS_DIR), modId)
        val deleted = modDir.deleteRecursively()
        if (deleted) loadActiveMods(context)
        return deleted
    }
}
