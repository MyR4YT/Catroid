package org.catrobat.catroid.modloader

import android.content.Context
import android.util.Log
import java.io.File

object ModManager {
    fun loadAllMods(context: Context) {
        val modDir = File(context.filesDir, "mods")
        if (!modDir.exists()) modDir.mkdirs()
        
        modDir.listFiles()?.forEach { file ->
            if (file.isDirectory) {
                val mainLua = File(file, "main.lua")
                if (mainLua.exists()) {
                    LuaExecutor.loadFile(mainLua.absolutePath)
                }
            }
        }
    }

    fun createDemoMod(context: Context) {
        val modDir = File(context.filesDir, "mods")
        val tweenDir = File(modDir, "TweenEngine")
        if (tweenDir.exists()) return
        
        tweenDir.mkdirs()
        File(tweenDir, "mod.json").writeText("{\"name\": \"Tween Engine\", \"description\": \"Mod nativo de interpolacao Lua\"}")
        File(tweenDir, "main.lua").writeText("catroid.registerBrick(\"Cubic Out\", \"Tweening\", \"applyCubicOut\", {\"steps\", \"distance\"}, \"#E91E63\")\n\nfunction applyCubicOut(args)\n    local steps = tonumber(args[\"steps\"]) or 10\n    catroid.log(\"Animacao Lua rodando!\")\nend")
    }
}
