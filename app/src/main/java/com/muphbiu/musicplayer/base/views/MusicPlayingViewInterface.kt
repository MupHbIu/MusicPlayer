package com.muphbiu.musicplayer.base.views

import com.muphbiu.musicplayer.base.BaseView

interface MusicPlayingViewInterface : BaseView {
    fun setRandomValue(value: Boolean)
    fun setRepeatValue(value: Int)
    fun setPlayPauseValue(value: Boolean)
    fun setSongName(name: String)
    fun setAuthor(author: String)
    fun setAlbum(album: String)
    fun setCurrentTime(time: Int)
    fun setSongDuration(duration: Int)

    fun showText(msg: String)
}