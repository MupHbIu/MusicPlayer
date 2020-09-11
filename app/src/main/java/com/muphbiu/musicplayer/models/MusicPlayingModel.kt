package com.muphbiu.musicplayer.models

import android.content.Context
import android.util.Log
import com.muphbiu.musicplayer.R
import com.muphbiu.musicplayer.base.models.MusicPlayingModelInterface
import com.muphbiu.musicplayer.data.PlayerInstanceValues
import com.muphbiu.musicplayer.data.PlaylistsManager
import java.io.File

class MusicPlayingModel(private var context: Context): MusicPlayingModelInterface {
    private val tag: String = "MusicPlayingPresenter"

    private val manager = PlaylistsManager(context)

    fun getPlaylist(playlistPath: String) : List<File> {
        return if(playlistPath != "")
            manager.getPlaylist(File(playlistPath).name)
        else
            manager.getPlaylistPlayingNow()
    }

    fun savePlayingPlaylist(playlist: String) {
        PlayerInstanceValues(context).savePlayingPlaylist(playlist)
    }
    fun saveSongPosition(position: Int) {
        PlayerInstanceValues(context).saveSongPosition(position)
    }
    fun saveCurrentTime(time: Int) {
        PlayerInstanceValues(context).saveCurrentTime(time)
    }
    fun saveRandom(random: Boolean) {
        PlayerInstanceValues(context).saveRandom(random)
    }
    fun savePlaying(playing: Boolean) {
        PlayerInstanceValues(context).savePlaying(playing)
    }
    fun saveRepeat(repeat: Int) {
        PlayerInstanceValues(context).saveRepeat(repeat)
    }

}