package org.catrobat.catroid.modloader

import android.content.Context
import java.io.File
import org.json.JSONObject
import org.json.JSONArray

object ModManager {
    private const val MODS_DIR = "mods"
    private val activeMods = mutableListOf<ModData>()

    data class ModData(val id: String, val name: String, val path: String, val categories: List<CustomCategory> = listOf())
    data class CustomCategory(val name: String, val color: String)

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
                    val categoriesFile = File(file, "categories.json")
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
                                customCats.add(CustomCategory(
                                    catJson.getString("name"),
                                    catJson.getString("color")
                                ))
                            }
                        } catch (e: Exception) { }
                    }

                    activeMods.add(ModData(file.name, name, file.absolutePath, customCats))
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

    fun getCustomCategories(): List<CustomCategory> {
        return activeMods.flatMap { it.categories }
    }

    fun deleteMod(context: Context, modId: String): Boolean {
        val modDir = File(File(context.filesDir, MODS_DIR), modId)
        val deleted = modDir.deleteRecursively()
        if (deleted) loadActiveMods(context)
        return deleted
    }
}
