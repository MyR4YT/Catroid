package com.myradev.lunarcode.modloader

import android.content.Context
import android.util.Log
import com.myradev.lunarcode.utils.ToastUtil
import java.io.File
import org.luaj.vm2.Globals
import org.luaj.vm2.lib.jse.JsePlatform
import org.luaj.vm2.LuaValue
import org.luaj.vm2.lib.ThreeArgFunction
import org.luaj.vm2.lib.OneArgFunction
import com.myradev.lunarcode.content.Sprite
import com.myradev.lunarcode.CatroidApplication

object ModManager {
    private const val MODS_DIR = "mods"
    private val activeMods = mutableListOf<ModData>()
    private lateinit var globals: Globals
    private val customCategories = mutableListOf<CustomCategory>()
    private val customBricks = mutableMapOf<String, LuaValue>()

    data class ModData(val id: String, val name: String, val path: String)
    data class CustomCategory(val name: String, val color: String)

    fun init(context: Context) {
        globals = JsePlatform.standardGlobals()
        setupLuaBridge(context)
        loadActiveMods(context)
    }

    private fun setupLuaBridge(context: Context) {
        val lunarLib = LuaValue.tableOf()
        
        lunarLib.set("register_brick", object : ThreeArgFunction() {
            override fun call(brickName: LuaValue, category: LuaValue, callback: LuaValue): LuaValue {
                customBricks[brickName.tojstring()] = callback
                Log.d("ModLua", "Registered brick: ${brickName.tojstring()}")
                return LuaValue.NIL
            }
        })

        lunarLib.set("toast", object : OneArgFunction() {
            override fun call(msg: LuaValue): LuaValue {
                ToastUtil.showSuccess(context, msg.tojstring())
                return LuaValue.NIL
            }
        })

        globals.set("lunar", lunarLib)
    }

    fun executeBrick(name: String, sprite: Sprite?) {
        val callback = customBricks[name]
        if (callback != null && callback.isfunction()) {
            try {
                callback.call(LuaValue.userdataOf(sprite))
            } catch (e: Exception) {
                Log.e("ModManager", "Lua execution error: ${e.message}")
            }
        }
    }

    private fun loadActiveMods(context: Context) {
        val modDir = File(context.filesDir, MODS_DIR)
        if (!modDir.exists()) modDir.mkdirs()
        modDir.listFiles()?.filter { it.isDirectory }?.forEach { file ->
            val luaFile = File(file, "main.lua")
            if (luaFile.exists()) {
                try {
                    globals.loadfile(luaFile.absolutePath).call()
                } catch (e: Exception) { Log.e("ModManager", "Lua Error: ${e.message}") }
            }
        }
    }

    fun getCustomBricks(): List<String> = customBricks.keys.toList()

    fun getOverrideFile(resourceName: String): File? {
        val context = CatroidApplication.getAppContext() ?: return null
        val modDir = File(context.filesDir, MODS_DIR)
        if (!modDir.exists()) return null
        modDir.listFiles()?.forEach { mod ->
            val override = File(mod, resourceName)
            if (override.exists()) return override
        }
        return null
    }
}
