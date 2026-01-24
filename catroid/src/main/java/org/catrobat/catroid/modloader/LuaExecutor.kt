package org.catrobat.catroid.modloader

import android.util.Log
import java.io.File
import org.luaj.vm2.Globals
import org.luaj.vm2.LuaValue
import org.luaj.vm2.lib.jse.JsePlatform
import org.luaj.vm2.lib.TwoArgFunction
import org.luaj.vm2.lib.OneArgFunction
import org.luaj.vm2.lib.ZeroArgFunction

object LuaExecutor {

    private val globals: Globals = JsePlatform.standardGlobals()

    init {
        // Registrar API 'catroid' no Lua
        val catroidTable = LuaValue.tableOf()
        
        // Função para mover X (Exemplo de ponte)
        catroidTable.set("moveX", object : OneArgFunction() {
            override fun call(arg: LuaValue): LuaValue {
                Log.i("LuaAPI", "MoveX chamado com: " + arg.todouble())
                // TODO: Conectar com Sprite real: SpriteManager.currentSprite.changeX(arg.todouble())
                return LuaValue.NIL
            }
        })

        // Função log
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
            if (func.isnil()) {
                Log.e("LuaExecutor", "Função nao encontrada: $functionName")
                return
            }

            // Converter mapa de argumentos para LuaValues (simplificado: passando 1o valor)
            // Num caso real, precisaria alinhar a ordem dos parametros ou passar tabela
            if (args.isNotEmpty()) {
                // Passa o primeiro valor como exemplo
                val firstVal = args.values.firstOrNull() ?: "0"
                func.call(LuaValue.valueOf(firstVal))
            } else {
                func.call()
            }
        } catch (e: Exception) {
            Log.e("LuaExecutor", "Erro na execução Lua: ${e.message}")
        }
    }

    // Carrega o arquivo e interpreta
    fun loadFile(path: String) {
        val file = File(path)
        if (!file.exists()) return

        try {
            // Executa o script para carregar funções na memoria
            globals.loadfile(path).call()
            
            // Parse manual APENAS para os registros (pois precisamos saber antes de rodar o bloco)
            // O resto da logica fica na memoria do LuaJ
            val lines = file.readLines()
            for (line in lines) {
                val trimmed = line.trim()
                if (trimmed.startsWith("catroid.registerBrick(")) {
                    parseRegisterLine(trimmed)
                }
            }
        } catch (e: Exception) {
            Log.e("LuaExecutor", "Erro ao carregar script: ${e.message}")
        }
    }

    private fun parseRegisterLine(line: String) {
        try {
            // Ex: catroid.registerBrick("Nome", "Cat", "func", {"p1", "p2"}, "#COLOR")
            val content = line.substringAfter("(").substringBeforeLast(")")
            val parts = content.split(",") // Split simples (falha se tiver virgula na string, mas ok por agora)
            
            if (parts.size >= 3) {
                val name = cleanStr(parts[0])
                val category = cleanStr(parts[1])
                val func = cleanStr(parts[2])
                
                val params = mutableListOf<String>()
                if (parts.size >= 4) {
                    val paramStr = parts[3].trim().removePrefix("{").removeSuffix("}")
                    if (paramStr.isNotEmpty()) {
                        paramStr.split(",").forEach { params.add(cleanStr(it)) }
                    }
                }

                val color = if (parts.size >= 5) cleanStr(parts[4]) else "#FFFFFF"

                LuaBrickRegistry.register(name, category, func, params, color)
            }
        } catch (e: Exception) {
            Log.e("LuaExecutor", "Erro parse register: $line")
        }
    }

    private fun cleanStr(s: String) = s.trim().replace(""", "").replace("'", "")
}
