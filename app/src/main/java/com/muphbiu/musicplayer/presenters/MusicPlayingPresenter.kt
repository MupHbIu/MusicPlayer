package com.muphbiu.musicplayer.presenters

import android.util.Log
import com.muphbiu.musicplayer.R
import com.muphbiu.musicplayer.base.MusicPlayingContract

class MusicPlayingPresenter(View: MusicPlayingContract.View) : MusicPlayingContract.Presenter {
    private val TAG: String = "MusicPlayingPresenter"

    private var activity: MusicPlayingContract.View = View

    private var message: String = ""

    override fun bntClicked() {
        // Get message from Repository (MusicPlayingContract.Repository)
        message = R.string.defStr.toString()
        activity.showText(message)
        Log.d(TAG, "btnClicked")
    }

    override fun onDestroy() {

        Log.d(TAG, "onDestroy()")
    }
}