package com.muphbiu.musicplayer.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.muphbiu.musicplayer.R
import com.muphbiu.musicplayer.base.interfaces.PlaylistAdapterListener
import com.muphbiu.musicplayer.base.views.PlaylistActivityViewInterface
import kotlinx.android.synthetic.main.item_playlist.view.*
import java.io.File

class PlaylistAdapter(private val activity: PlaylistAdapterListener, private var items: List<File>) :
    RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder>() {

    class PlaylistViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val item : LinearLayout = view.playlistItem
        val name : TextView = view.playlistTextName
        val author : TextView = view.playlistTextAuthor
        val img : ImageView = view.playlistImg
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_playlist, parent, false)
        return PlaylistViewHolder(view)
    }
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.name.text = items[position].name
        holder.author.text = items[position].absolutePath
        holder.img.setImageResource(R.drawable.ic_baseline_play_circle_filled_24)
        holder.item.setOnClickListener { itemClicked(items[position]) }
    }

    private fun itemClicked(item: File) {
        activity.itemSelected(item)
    }
}