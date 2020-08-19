package com.muphbiu.musicplayer.base.views

import com.muphbiu.musicplayer.base.BaseView
import java.io.File

interface StartActivityViewInterface : BaseView {
    fun updatePlaylistView()
    fun updatePlaylist(playlist: List<File>)

    fun updateFileView()
    fun updateFiles(files: List<File>)

    fun playSong(songPath: String)
    fun getLocationToSecondDir() : String
    fun getNewFileList(location: String)
    fun showCreateNewPlaylistDialog()
    fun playlistNameGot(playlistName: String)
    fun playlistFileGot(playlistFile: String)
}