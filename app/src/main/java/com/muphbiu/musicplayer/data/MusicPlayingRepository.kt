package com.muphbiu.musicplayer.data

import com.muphbiu.musicplayer.R
import com.muphbiu.musicplayer.base.MusicPlayingContract

class MusicPlayingRepository: MusicPlayingContract.Repository {
    private val TAG: String = "MusicPlayingPresenter"

    private var message: String = ""

    override fun loadMessage(): String {
        message = R.string.defStr.toString()
        //message = "New message"
        return message
    }
}