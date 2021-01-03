package com.muphbiu.musicplayer.ui.adapters

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.muphbiu.musicplayer.R
import com.muphbiu.musicplayer.base.interfaces.PlaylistAdapterListener
import kotlinx.android.synthetic.main.item_playlist.view.*
import java.io.File

class PlayingListAdapter(private val activity: PlaylistAdapterListener, private val items: List<File>) :
    RecyclerView.Adapter<PlayingListAdapter.PlayingListViewHolder>() {

    private var selectedItem: String? = null

    class PlayingListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val item : LinearLayout = view.playlistItem
        val name : TextView = view.playlistTextName
        val author : TextView = view.playlistTextAuthor
        val img : ImageView = view.playlistImg
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayingListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_playlist, parent, false)
        return PlayingListViewHolder(view)
    }
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PlayingListViewHolder, position: Int) {
        holder.name.text = items[position].name
        holder.author.text = items[position].absolutePath
        holder.img.setImageResource(R.drawable.ic_baseline_play_circle_filled_24)
        holder.item.setOnClickListener { itemClicked(position) }
        holder.item.setOnCreateContextMenuListener { menu, view, contextMenuInfo ->
            selectedItem = holder.name.text.toString()
            val deleteSongFromPlaylist = menu?.add(Menu.NONE, 2, Menu.NONE, "Delete song from playlist")
            deleteSongFromPlaylist?.setOnMenuItemClickListener { activity.showDialogDelete(holder.name.text.toString()); true }
        }
    }

    private fun itemClicked(position: Int) {
        activity.itemSelected(position)
    }

}