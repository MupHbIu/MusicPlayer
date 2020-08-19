package com.muphbiu.musicplayer.base.views

import com.muphbiu.musicplayer.base.BaseView
import java.io.File

interface PlaylistActivityViewInterface : BaseView {
    fun updateView()
    fun updatePlaylist(playlist: List<File>)

}