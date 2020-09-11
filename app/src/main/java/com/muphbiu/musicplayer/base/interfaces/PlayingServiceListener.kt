package com.muphbiu.musicplayer.base.interfaces

interface PlayingServiceListener {
    fun updateView()
    fun updateCurrentTime(time: Int)
    fun updateRepeatMode(value: Int)
    fun updateRandomMode(value: Boolean)
    fun updatePlayPause(value: Boolean)
    fun setServiceStatus(status: Boolean)
}