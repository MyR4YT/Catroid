package org.catrobat.catroid.modloader

import android.util.Log
import java.io.File
import org.luaj.vm2.Globals
import org.luaj.vm2.LuaValue
import org.luaj.vm2.lib.jse.JsePlatform
import org.luaj.vm2.lib.OneArgFunction
import org.luaj.vm2.lib.LibFunction
import org.luaj.vm2.lib.VarArgFunction
import org.luaj.vm2.Varargs

object LuaExecutor {

    private val globals: Globals = JsePlatform.standardGlobals()

    init {
        val catroidTable = LuaValue.tableOf()
        
        // API: catroid.registerBrick(name, category, funcName, {params}, color)
        catroidTable.set("registerBrick", object : VarArgFunction() {
            override fun oncall(args: Varargs): Varargs {
                val name = args.checkjstring(1)
                val category = args.checkjstring(2)
                val funcName = args.checkjstring(3)
                
                val params = mutableListOf<String>()
                if (args.narg() >= 4 && args.istable(4)) {
                    val table = args.checktable(4)
                    var i = 1
                    while (true) {
                        val v = table.get(i++)
                        if (v.isnil()) break
                        params.add(v.tojstring())
                    }
                }
                
                val color = if (args.narg() >= 5) args.checkjstring(5) else "#FFFFFF"
                
                LuaBrickRegistry.register(name, category, funcName, params, color)
                Log.d("LuaExecutor", "Bloco registrado via Lua: $name")
                return LuaValue.NIL
            }
        })

        catroidTable.set("moveX", object : OneArgFunction() {
            override fun call(arg: LuaValue): LuaValue {
                Log.i("LuaAPI", "MoveX: " + arg.todouble())
                return LuaValue.NIL
            }
        })

        catroidTable.set("log", object : OneArgFunction() {
            override fun call(arg: LuaValue): LuaValue {
                Log.d("LuaScript", arg.tojstring())
                return LuaValue.NIL
            }
        })

        globals.set("catroid", catroidTable)
    }

    fun executeFunction(functionName: String, args: Map<String, String>) {
        try {
            val func = globals.get(functionName)
            if (func.isnil()) return

            val table = LuaValue.tableOf()
            args.forEach { (k, v) -> table.set(k, LuaValue.valueOf(v)) }
            func.call(table)
        } catch (e: Exception) {
            Log.e("LuaExecutor", "Erro exec: ${e.message}")
        }
    }

    fun loadFile(path: String) {
        val file = File(path)
        if (!file.exists()) return
        try {
            globals.loadfile(path).call()
            Log.d("LuaExecutor", "Arquivo carregado: $path")
        } catch (e: Exception) {
            Log.e("LuaExecutor", "Erro load: ${e.message}")
        }
    }

    private fun cleanStr(s: String) = s.trim().replace("\"", "").replace("\u0027", "")
}
