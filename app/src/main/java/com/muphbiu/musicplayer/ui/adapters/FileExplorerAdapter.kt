package com.muphbiu.musicplayer.ui.adapters

import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.muphbiu.musicplayer.R
import com.muphbiu.musicplayer.base.interfaces.FileExplorerAdapterListener
import com.muphbiu.musicplayer.base.views.FileExplorerViewInterface
import kotlinx.android.synthetic.main.item_file_explorer.view.*
import java.io.File

class FileExplorerAdapter(private var activity: FileExplorerAdapterListener, fileList : List<File>) :
    RecyclerView.Adapter<FileExplorerAdapter.FileExplorerViewHolder>() {

    private var files : MutableList<File> = fileList.toMutableList()

    class FileExplorerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val item: LinearLayout = view.fileExplorerItem
        val img: ImageView = view.fileExplorerImg
        val text: TextView = view.fileExplorerText
        var loc = ""
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileExplorerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_file_explorer, parent, false)
        return FileExplorerViewHolder(view)
    }

    override fun onBindViewHolder(holder: FileExplorerViewHolder, position: Int) {
        if(position == 0 &&
            (files[position].absolutePath.toString() != activity.getLocationToMainDir() ||
             File(File(files[1].parent).parent).toString() == activity.getLocationToMainDir())
        ) {
            holder.text.text = ".."
        } else {
            holder.text.text = files[position].name
            holder.item.setOnCreateContextMenuListener { menu, view, contextMenuInfo ->
                val addToPlayListMenuItem = menu?.add(Menu.NONE, 1, Menu.NONE, "Add to playlist...")
                addToPlayListMenuItem?.setOnMenuItemClickListener { activity.showAddToPlayList(holder.loc); true }
            }
        }
        holder.loc = files[position].absolutePath
        if (files[position].isDirectory)
            holder.img.setImageResource(R.drawable.ic_baseline_folder_24)
        else
            holder.img.setImageResource(R.drawable.ic_baseline_play_circle_filled_24)
        holder.item.setOnClickListener { itemClicked(File(holder.loc)) }
    }

    override fun getItemCount(): Int {
        return files.size
    }

    private fun itemClicked(file: File) {
        if (file.isDirectory) {
            activity.showFolder(file)
        } else
            activity.showFile(file)
    }
}