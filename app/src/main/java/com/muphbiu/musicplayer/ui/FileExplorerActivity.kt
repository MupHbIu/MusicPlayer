package com.muphbiu.musicplayer.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.muphbiu.musicplayer.R
import com.muphbiu.musicplayer.base.views.FileExplorerViewInterface
import com.muphbiu.musicplayer.presenters.FileExplorerPresenter
import com.muphbiu.musicplayer.ui.adapters.FileExplorerAdapter
import com.muphbiu.musicplayer.ui.fragments.PlayerDialogFragment
import kotlinx.android.synthetic.main.activity_file_explorer.*
import java.io.File

class FileExplorerActivity : AppCompatActivity(), FileExplorerViewInterface {
    private var files = mutableListOf<File>()
    private lateinit var presenter : FileExplorerPresenter
    private lateinit var adapter : FileExplorerAdapter
    private lateinit var dialog : PlayerDialogFragment
    private var songsToPlaylist = ""
    private lateinit var viewFolder : File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_explorer)

        presenter = FileExplorerPresenter(this, this)
        files = presenter.getFileList(getLocationToSecondDir()).toMutableList()
        adapter = FileExplorerAdapter(this, files)

        fileExplorerRecycleView.layoutManager = LinearLayoutManager(this)
        fileExplorerRecycleView.adapter = adapter

        viewFolder = File(getLocationToSecondDir())
        //fileExplorerButtonBack.setOnClickListener { super.onBackPressed() }
    }

    override fun onBackPressed() {
        when (viewFolder.absolutePath) {
            getLocationToSecondDir() -> super.onBackPressed()
            getLocationToMainDir() -> showFolder(File(File(viewFolder.parent).parent))
            else -> showFolder(File(viewFolder.parent))
        }
    }

    override fun getNewFileList(location: String): List<File> {
        return presenter.getFileList(location)
    }

    override fun getLocationToMainDir(): String {
        return presenter.getMainDirLocation()
    }
    override fun getLocationToSecondDir(): String {
        return presenter.getSecondDirLocation()
    }

    override fun showFolder(folder: File) {
        viewFolder = folder
        files = getNewFileList(folder.absolutePath).toMutableList()
        adapter = FileExplorerAdapter(this, files)
        fileExplorerRecycleView.adapter = adapter
    }
    override fun showFile(file: File) {
        presenter.openFile(file)
    }

    override fun showAddToPlayList(location: String) {
        dialog = PlayerDialogFragment(this, false)
        dialog.addPlaylists(presenter.getListPlaylists())
        dialog.show(supportFragmentManager, "playlists")
        songsToPlaylist = location
    }
    override fun showCreateNewPlaylistDialog() {
        dialog = PlayerDialogFragment(this, true)
        dialog.show(supportFragmentManager, "newPlaylist")
    }
    override fun playlistNameGot(playlistName: String) {
        val playlistPath = presenter.createNewPlaylist(playlistName)
        if(playlistPath != "") {
            playlistFileGot(playlistPath)
            showMessage("Playlist created.")
        } else {
            showMessage("Error! Playlist not created!")
        }
    }
    override fun playlistFileGot(playlistFile: String) {
        if(presenter.addToPlaylist(songsToPlaylist, playlistFile))
            showMessage("Song $songsToPlaylist added to playlist $playlistFile")
        else
            showMessage("Error! Song not added")
    }

    // ========== D E F A U L T ===========
    override fun showLoad() {
        TODO("Not yet implemented")
    }

    override fun hideLoad() {
        TODO("Not yet implemented")
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}