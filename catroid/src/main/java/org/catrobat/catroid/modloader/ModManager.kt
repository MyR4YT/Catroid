package org.catrobat.catroid.modloader

import android.content.Context
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
}
