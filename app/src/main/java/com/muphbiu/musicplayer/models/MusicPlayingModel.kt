package com.muphbiu.musicplayer.models

import android.content.Context
import android.util.Log
import com.muphbiu.musicplayer.R
import com.muphbiu.musicplayer.base.models.MusicPlayingModelInterface

class MusicPlayingModel(private var context: Context): MusicPlayingModelInterface {
    private val tag: String = "MusicPlayingPresenter"

    private var message: String = ""

    override fun loadMessage(): String {
        Log.d(tag, "loadMessage()")
        message = context.resources.getString(R.string.newStr)
        return message
    }
}