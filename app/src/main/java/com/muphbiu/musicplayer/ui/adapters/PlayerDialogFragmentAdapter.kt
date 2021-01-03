package com.muphbiu.musicplayer.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.muphbiu.musicplayer.R
import com.muphbiu.musicplayer.base.interfaces.PlayerDialogFragmentInterface
import kotlinx.android.synthetic.main.item_player_dialor_recyclerview.view.*
import java.io.File

class PlayerDialogFragmentAdapter(private val fragment: PlayerDialogFragmentInterface, private var playlists: List<File>) : RecyclerView.Adapter<PlayerDialogFragmentAdapter.PlayerDialogViewHolder>() {

    class PlayerDialogViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val item : LinearLayout = view.playerDialogRecyclerviewItem
        val text: TextView = view.playerDialogRecyclerviewText
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerDialogViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_player_dialor_recyclerview, parent, false)
        return PlayerDialogViewHolder(view)
    }
    override fun getItemCount(): Int {
        return playlists.size
    }

    override fun onBindViewHolder(holder: PlayerDialogViewHolder, position: Int) {
        holder.text.text = playlists[position].name
        holder.item.setOnClickListener {
            fragment.playlistGot(playlists[position].absolutePath)
        }
    }

}