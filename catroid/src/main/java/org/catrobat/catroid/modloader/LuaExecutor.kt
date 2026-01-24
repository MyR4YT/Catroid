package org.catrobat.catroid.modloader

import android.util.Log
import java.io.File
import org.luaj.vm2.Globals
import org.luaj.vm2.LuaValue
import org.luaj.vm2.lib.jse.JsePlatform
import org.luaj.vm2.lib.OneArgFunction
import org.luaj.vm2.lib.VarArgFunction
import org.luaj.vm2.Varargs

object LuaExecutor {
    private val globals: Globals = JsePlatform.standardGlobals()

    init {
        val catroidTable = LuaValue.tableOf()
        
        // Registro: name, category, func, {params}, color
        catroidTable.set("registerBrick", object : VarArgFunction() {
            override fun invoke(args: Varargs): Varargs {
                try {
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
                    val color = if (args.narg() >= 5) args.checkjstring(5) else "#E91E63"
                    LuaBrickRegistry.register(name, category, funcName, params, color)
                    Log.d("LuaExecutor", "Bloco registrado: $name")
                } catch (e: Exception) {
                    Log.e("LuaExecutor", "Erro ao registrar bloco: ${e.message}")
                }
                return LuaValue.NIL
            }
        })

        catroidTable.set("log", object : OneArgFunction() {
            override fun call(arg: LuaValue): LuaValue {
                Log.d("LuaMod", arg.tojstring())
                return LuaValue.NIL
            }
        })

        globals.set("catroid", catroidTable)
    }

    fun loadFile(path: String) {
        val file = File(path)
        if (!file.exists()) return
        try {
            globals.loadfile(path).call()
            Log.d("LuaExecutor", "Mod carregado: ${file.parentFile.name}")
        } catch (e: Exception) {
            Log.e("LuaExecutor", "Erro ao rodar script Lua: ${e.message}")
        }
    }

    fun executeFunction(name: String, args: Map<String, String>) {
        try {
            val func = globals.get(name)
            if (func.isnil()) return
            val table = LuaValue.tableOf()
            args.forEach { (k, v) -> table.set(k, LuaValue.valueOf(v)) }
            func.call(table)
        } catch (e: Exception) {
            Log.e("LuaExecutor", "Erro na funcao $name: ${e.message}")
        }
    }
}
