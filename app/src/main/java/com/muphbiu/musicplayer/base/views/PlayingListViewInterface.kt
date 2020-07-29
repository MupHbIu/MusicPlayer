package com.muphbiu.musicplayer.base.views

import com.muphbiu.musicplayer.base.BaseView
import com.muphbiu.musicplayer.data.Song

interface PlayingListViewInterface : BaseView {
    fun showPlayingList(SongList: List<Song>)
}