package com.muphbiu.musicplayer.models

import android.content.Context
import com.muphbiu.musicplayer.base.models.PlayingListModelInterface
import com.muphbiu.musicplayer.data.PlaylistsManager
import java.io.File

class PlayingListModel(context: Context) : PlayingListModelInterface {

    private val manager = PlaylistsManager(context)

    fun getPlaylist(playlistPath: String) : List<File> {
        return manager.getPlaylist(File(playlistPath).name)
    }
}