 package com.muphbiu.musicplayer.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.muphbiu.musicplayer.R
import com.muphbiu.musicplayer.base.interfaces.PlaylistAdapterListener
import com.muphbiu.musicplayer.base.views.PlayingListViewInterface
import com.muphbiu.musicplayer.presenters.PlayingListPresenter
import com.muphbiu.musicplayer.ui.adapters.PlayingListAdapter
import kotlinx.android.synthetic.main.activity_playing_list.*
import java.io.File

 class PlayingListActivity : AppCompatActivity(), PlayingListViewInterface, PlaylistAdapterListener {
     companion object {
         const val PLAYLIST_NAME = "PlaylistName"
     }
     private val tag = "PlayingListActivity"

     private lateinit var presenter: PlayingListPresenter
     private lateinit var adapter: PlayingListAdapter

     private var files = mutableListOf<File>()
     private var playlistPath = "/"

     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_playing_list)

         playlistBack.setOnClickListener { super.onBackPressed() }

         presenter = PlayingListPresenter(this, this)

     }
     override fun onDestroy() {
         super.onDestroy()
     }

     // ========== Set parameters ==========
     override fun setPlaylistPath(playlistPath: String) {
         this.playlistPath = playlistPath
     }
     override fun setPlaylist(playlist: List<File>) {
        files = playlist.toMutableList()
     }
     override fun updatePlaylist() {
         playlistTitle.text = File(playlistPath).name
         adapter = PlayingListAdapter(this, files)
         playlistRecyclerView.layoutManager = LinearLayoutManager(this)
         playlistRecyclerView.adapter = adapter
     }
     // ========== Set parameters ==========

     // ==========  ==========
     override fun itemSelected(position: Int) {
         showMessage("Song position: $position")
     }

     override fun showDialogDelete(name: String) {
         showMessage("Delete song from playlist")
         // TODO: Delete song from playlist
     }
     override fun showDialogRename(name: String) {}
     // ==========  ==========

     // ========== D E F A U L T ==========
     override fun showMessage(message: String) {
         Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
     }
     override fun showLoad() {}
     override fun hideLoad() {}
 }