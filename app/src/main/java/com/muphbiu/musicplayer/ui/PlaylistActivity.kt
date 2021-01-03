package com.muphbiu.musicplayer.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.muphbiu.musicplayer.R
import com.muphbiu.musicplayer.base.interfaces.PlaylistAdapterListener
import com.muphbiu.musicplayer.base.views.PlaylistActivityViewInterface
import com.muphbiu.musicplayer.presenters.PlaylistPresenter
import com.muphbiu.musicplayer.ui.adapters.PlaylistAdapter
import kotlinx.android.synthetic.main.fragment_list.*
import java.io.File

class PlaylistActivity : AppCompatActivity(), PlaylistActivityViewInterface,
    PlaylistAdapterListener {
    companion object{
        private const val KEY_PLAYLIST = "isPlaylist"
    }

    private lateinit var presenter: PlaylistPresenter
    private lateinit var adapter: PlaylistAdapter

    private var files = mutableListOf<File>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_list)

        presenter = PlaylistPresenter(this, this)

        val playlistPath = intent.getStringExtra(KEY_PLAYLIST) ?: ""
        presenter.getData(playlistPath)

        updateView()
    }

    override fun updatePlaylist(playlist: List<File>) {
        files = playlist.toMutableList()
    }

    override fun updateView() {
        adapter = PlaylistAdapter(this, files)
        fragmentListRecyclerView.layoutManager = LinearLayoutManager(this)
        fragmentListRecyclerView.adapter = adapter
    }

    override fun itemSelected(item: Int) {

        showMessage(files[item].name)
    }


    override fun showDialogRename(name: String) {
        TODO("Not yet implemented")
    }
    override fun showDialogDelete(name: String) {
        TODO("Not yet implemented")
    }



    // ========== D E F A U L T ==========
    override fun showLoad() {
        TODO("Not yet implemented")
    }
    override fun hideLoad() {
        TODO("Not yet implemented")
    }
    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.activityDestroyed()
    }
}