package com.muphbiu.musicplayer.presenters

import android.content.Context
import android.util.Log
import com.muphbiu.musicplayer.R
import com.muphbiu.musicplayer.base.MusicPlayingContract
import com.muphbiu.musicplayer.data.MusicPlayingRepository

class MusicPlayingPresenter(View: MusicPlayingContract.View, context: Context) : MusicPlayingContract.Presenter {
    private val TAG: String = "MusicPlayingPresenter"

    private var activity: MusicPlayingContract.View = View
    private var repository: MusicPlayingContract.Repository = MusicPlayingRepository(context)

    private var message: String = ""

    override fun bntClicked() {
        // Get message from Repository (MusicPlayingContract.Repository)
        message = repository.loadMessage()
        activity.showText(message)
        Log.d(TAG, "btnClicked")
    }

    override fun onDestroy() {

        Log.d(TAG, "onDestroy()")
    }
}