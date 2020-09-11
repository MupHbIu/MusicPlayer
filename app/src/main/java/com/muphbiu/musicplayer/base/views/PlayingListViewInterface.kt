package com.muphbiu.musicplayer.base.views

import com.muphbiu.musicplayer.base.BaseView
import java.io.File

interface PlayingListViewInterface : BaseView {
    fun setPlaylistPath(playlistPath: String)
    fun setPlaylist(playlist: List<File>)
    fun updatePlaylist()
}