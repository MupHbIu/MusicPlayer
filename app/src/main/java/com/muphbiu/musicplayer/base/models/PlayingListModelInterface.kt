package com.muphbiu.musicplayer.base.models

import com.muphbiu.musicplayer.data.Song

interface PlayingListModelInterface {
    fun loadPlayingList() : List<Song>
}