package com.muphbiu.musicplayer.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muphbiu.musicplayer.R
import com.muphbiu.musicplayer.base.interfaces.FragmentListAdapterListener
import java.io.File

class FragmentListAdapter() : RecyclerView.Adapter<FragmentListAdapter.FragmentListViewHolder>() {
    private lateinit var listener: FragmentListAdapterListener
    private lateinit var list: List<File>
    private var id = 0

    class FragmentListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    fun setListener(listener: FragmentListAdapterListener) {
        this.listener = listener
    }
    fun setList(list: List<File>) {
        this.list = list
    }
    fun setTypeOfList(id: Int) {
        this.id = id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FragmentListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_playlist, parent, false)
        return FragmentListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: FragmentListViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}