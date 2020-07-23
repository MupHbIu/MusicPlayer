package com.muphbiu.musicplayer.models

import android.content.Context
import com.muphbiu.musicplayer.base.models.PlayingListModelInterface
import com.muphbiu.musicplayer.data.Song
import com.muphbiu.musicplayer.data.database.PlayingListDB

class PlayingListModel(context: Context) : PlayingListModelInterface {

    private val playingList: PlayingListDB = PlayingListDB(context)

    override fun loadPlayingList(): List<Song> {
        return playingList.getFromDB()
    }
}