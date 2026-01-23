package org.catrobat.catroid.modloader

import android.content.Context
import java.io.File
import org.json.JSONObject

object ModManager {
    private const val MODS_DIR = "mods"
    private val activeMods = mutableListOf<ModData>()

    data class ModData(val id: String, val name: String, val path: String)

    fun init(context: Context) {
        loadActiveMods(context)
    }

    private fun loadActiveMods(context: Context) {
        activeMods.clear()
        val modDir = File(context.filesDir, MODS_DIR)
        if (modDir.exists()) {
            modDir.listFiles()?.forEach { file ->
                if (file.isDirectory) {
                    val infoFile = File(file, "mod_info.json")
                    var name = file.name
                    if (infoFile.exists()) {
                        try {
                            val json = JSONObject(infoFile.readText())
                            name = json.optString("name", name)
                        } catch (e: Exception) { }
                    }
                    activeMods.add(ModData(file.name, name, file.absolutePath))
                }
            }
        }
    }

    fun getOverrideFile(resourceName: String): File? {
        // Ex: resourceName = "looks/cat.png"
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
