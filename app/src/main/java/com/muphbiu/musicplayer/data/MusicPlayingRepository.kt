package com.muphbiu.musicplayer.data

import android.content.Context
import com.muphbiu.musicplayer.R
import com.muphbiu.musicplayer.base.MusicPlayingContract

class MusicPlayingRepository(private var context: Context): MusicPlayingContract.Repository {
    private val TAG: String = "MusicPlayingPresenter"

    private var message: String = ""

    override fun loadMessage(): String {
        message = context.resources.getString(R.string.newStr)
        //message = "New message"
        return message
    }
}