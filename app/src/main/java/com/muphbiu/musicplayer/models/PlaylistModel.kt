package com.muphbiu.musicplayer.models

import android.content.Context
import com.muphbiu.musicplayer.data.PlaylistsManager
import java.io.File

class PlaylistModel(context: Context) {

    private val manager = PlaylistsManager(context)

    fun getPlaylist(path: String) : List<File> {
        return manager.getPlaylist(path)
    }

    fun getListOfPlaylist() : List<File> {
        return manager.getListOfPlaylist()
    }

}