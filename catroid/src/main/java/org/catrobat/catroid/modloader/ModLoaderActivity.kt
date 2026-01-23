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

class ModLoaderActivity : BaseActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ModAdapter
    private val modList = mutableListOf<ModItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mod_loader)
        
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.main_menu_mods)

        recyclerView = findViewById(R.id.mod_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ModAdapter(modList) { modId ->
n		val emptyText = findViewById<TextView>(R.id.empty_mods_text)
            if (ModManager.deleteMod(this, modId)) {
                ToastUtil.showSuccess(this, "Mod deleted")
                loadMods()
            }
        }
        recyclerView.adapter = adapter

        findViewById<FloatingActionButton>(R.id.fab_add_mod).setOnClickListener {
             val intent = Intent(Intent.ACTION_GET_CONTENT)
             intent.type = "*/*"
             startActivityForResult(intent, PICK_MOD_REQUEST_CODE)
        }

        loadMods()
    }

    private fun loadMods() {
        modList.clear()
        val modDir = File(filesDir, "mods")
        if (!modDir.exists()) modDir.mkdirs()
        
        modDir.listFiles()?.forEach { file ->
            if (file.isDirectory) {
                val infoFile = File(file, "mod_info.json")
                var name = file.name
                var author = "Unknown"
                if (infoFile.exists()) {
                    try {
                        val json = JSONObject(infoFile.readText())
                        name = json.optString("name", name)
                        author = json.optString("author", "Unknown")
                    } catch (e: Exception) { }
                }
                modList.add(ModItem(file.name, name, author, file.absolutePath))
            }
        }
        adapter.notifyDataSetChanged()
        findViewById<TextView>(R.id.empty_mods_text).visibility = if (modList.isEmpty()) View.VISIBLE else View.GONE
        ModManager.init(this) 
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_MOD_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
             data?.data?.let { uri ->
                 try {
                     val inputStream = contentResolver.openInputStream(uri)
                     val tempFile = File(cacheDir, "temp_mod.cmod")
                     val outputStream = FileOutputStream(tempFile)
                     inputStream?.copyTo(outputStream)
                     inputStream?.close()
                     outputStream.close()
                     
                     val modName = "Mod_" + System.currentTimeMillis()
                     val targetDir = File(File(filesDir, "mods"), modName)
                     targetDir.mkdirs()
                     
                     ZipArchiver().unzip(tempFile, targetDir)
                     
                     ToastUtil.showSuccess(this, "Mod imported")
                     loadMods()
                 } catch (e: Exception) {
                     ToastUtil.showError(this, "Import failed")
                 }
             }
        }
    }

    companion object {
        private const val PICK_MOD_REQUEST_CODE = 1001
    }
}

data class ModItem(val id: String, val name: String, val author: String, val path: String)

class ModAdapter(private val mods: List<ModItem>, private val onDelete: (String) -> Unit) : RecyclerView.Adapter<ModAdapter.ModViewHolder>() {

    class ModViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameView: TextView = view.findViewById(R.id.mod_name)
        val authorView: TextView = view.findViewById(R.id.mod_author)
        val iconView: ImageView = view.findViewById(R.id.mod_icon)
        val deleteBtn: ImageButton = view.findViewById(R.id.btn_delete_mod)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mod, parent, false)
        return ModViewHolder(view)
    }

    override fun onBindViewHolder(holder: ModViewHolder, position: Int) {
        val mod = mods[position]
        holder.nameView.text = mod.name
        holder.authorView.text = "By: " + mod.author
        
        val iconFile = File(mod.path, "icon.png")
        if (iconFile.exists()) {
             holder.iconView.setImageBitmap(BitmapFactory.decodeFile(iconFile.absolutePath))
        } else {
             holder.iconView.setImageResource(R.drawable.ic_placeholder)
        }

        holder.deleteBtn.setOnClickListener { onDelete(mod.id) }
    }

    override fun getItemCount() = mods.size
}
