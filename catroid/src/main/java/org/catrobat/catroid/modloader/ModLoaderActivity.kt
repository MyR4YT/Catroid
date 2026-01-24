package org.catrobat.catroid.modloader

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import android.widget.ImageButton
import android.graphics.BitmapFactory
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.catrobat.catroid.R
import org.catrobat.catroid.ui.BaseActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.catrobat.catroid.utils.ToastUtil
import org.catrobat.catroid.io.ZipArchiver
import java.io.File
import java.io.FileOutputStream
import org.json.JSONObject
import java.nio.charset.Charset

class ModLoaderActivity : BaseActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ModAdapter
    private val modList = mutableListOf<Mod>()
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mod_loader)
        
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Mods"

        recyclerView = findViewById(R.id.mod_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ModAdapter(modList, this::onDeleteMod, this::onModClicked)
        recyclerView.adapter = adapter

        findViewById<FloatingActionButton>(R.id.fab_add_mod).setOnClickListener {
             val intent = Intent(Intent.ACTION_GET_CONTENT)
             intent.type = "application/zip" // Restrict to zip/archives if possible
             startActivityForResult(intent, PICK_MOD_REQUEST_CODE)
        }

        loadMods()
    }

    private fun loadMods() {
        modList.clear()
        val modDir = File(filesDir, "mods")
        if (!modDir.exists()) {
            modDir.mkdirs()
        }
        
        modDir.listFiles()?.forEach { file ->
            if (file.isDirectory) {
                var modName = file.name
                var modDescription = "No description"
                
                // Try to read mod.json
                val jsonFile = File(file, "mod.json")
                if (jsonFile.exists()) {
                    try {
                        val jsonContent = jsonFile.readText(Charset.defaultCharset())
                        val jsonObject = JSONObject(jsonContent)
                        if (jsonObject.has("name")) modName = jsonObject.getString("name")
                        if (jsonObject.has("description")) modDescription = jsonObject.getString("description")
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                modList.add(Mod(modName, modDescription, file.absolutePath, file))
            }
        }
        adapter.notifyDataSetChanged()
    }

    private fun onDeleteMod(mod: Mod) {
        try {
            mod.file.deleteRecursively()
            ToastUtil.showSuccess(this, "Mod deleted")
            loadMods()
        } catch (e: Exception) {
            e.printStackTrace()
            ToastUtil.showError(this, "Failed to delete mod")
        }
    }

    private fun onModClicked(mod: Mod) {
        // Here we can trigger the Lua execution
        val mainLua = File(mod.file, "main.lua")
        if (mainLua.exists()) {
            LuaExecutor.loadFile(mainLua.absolutePath)
            ToastUtil.showSuccess(this, "Loaded Lua script")
        } else {
            ToastUtil.showError(this, "main.lua not found in mod")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_MOD_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
             data?.data?.let { uri ->
                 try {
                     val inputStream = contentResolver.openInputStream(uri)
                     val modDir = File(filesDir, "mods")
                     if (!modDir.exists()) modDir.mkdirs()
                     
                     // Temporary file for zip
                     val tempFile = File(cacheDir, "temp_mod.zip")
                     val outputStream = FileOutputStream(tempFile)
                     inputStream?.copyTo(outputStream)
                     inputStream?.close()
                     outputStream.close()
                     
                     // Use filename from URI if possible, or timestamp as fallback for folder name
                     // But we want a cleaner folder name if possible. 
                     // For now, unique folder name is safe.
                     val modFolderName = "Mod_" + System.currentTimeMillis()
                     val targetDir = File(modDir, modFolderName)
                     targetDir.mkdirs()
                     
                     ZipArchiver().unzip(tempFile, targetDir)
                     
                     ToastUtil.showSuccess(this, "Mod imported successfully")
                     loadMods()
                 } catch (e: Exception) {
                     e.printStackTrace()
                     ToastUtil.showError(this, "Failed to import mod")
                 }
             }
        }
    }

    companion object {
        private const val PICK_MOD_REQUEST_CODE = 1001
    }
}

data class Mod(val name: String, val description: String, val path: String, val file: File)

class ModAdapter(
    private val mods: List<Mod>, 
    private val deleteCallback: (Mod) -> Unit,
    private val clickCallback: (Mod) -> Unit
) : RecyclerView.Adapter<ModAdapter.ModViewHolder>() {

    class ModViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameView: TextView = view.findViewById(R.id.mod_name)
        val descView: TextView = view.findViewById(R.id.mod_description)
        val iconView: ImageView = view.findViewById(R.id.mod_icon)
        val deleteBtn: ImageButton = view.findViewById(R.id.btn_delete_mod)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mod, parent, false)
        return ModViewHolder(view)
    }

    override fun onBindViewHolder(holder: ModViewHolder, position: Int) {
        val mod = mods[position]
        holder.nameView.text = mod.name
        holder.descView.text = mod.description
        
        val iconFile = File(mod.path, "icon.png")
        if (iconFile.exists()) {
             val bitmap = BitmapFactory.decodeFile(iconFile.absolutePath)
             holder.iconView.setImageBitmap(bitmap)
        } else {
             holder.iconView.setImageResource(R.drawable.ic_placeholder)
        }

        holder.deleteBtn.setOnClickListener {
            deleteCallback(mod)
        }
        
        holder.itemView.setOnClickListener {
            clickCallback(mod)
        }
    }

    override fun getItemCount() = mods.size
}
