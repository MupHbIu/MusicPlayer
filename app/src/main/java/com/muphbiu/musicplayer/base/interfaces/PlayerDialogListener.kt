package com.muphbiu.musicplayer.base.interfaces

interface PlayerDialogListener {
    fun showCreateNewPlaylistDialog()
    fun showMessage(message : String)
    fun playlistNameGot(playlistName: String)
    fun playlistFileGot(playlistFile: String)
}