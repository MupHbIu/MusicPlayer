package com.muphbiu.musicplayer.presenters

import android.content.Context
import android.util.Log
import com.muphbiu.musicplayer.base.presenters.MusicPlayingPresenterInterface
import com.muphbiu.musicplayer.base.views.MusicPlayingViewInterface
import com.muphbiu.musicplayer.models.MusicPlayingModel

class MusicPlayingPresenter(View: MusicPlayingViewInterface, context: Context) : MusicPlayingPresenterInterface {
    private val tag: String = "MusicPlayingPresenter"

    private var activity: MusicPlayingViewInterface = View
    private var model: MusicPlayingModel = MusicPlayingModel(context)

    private var message: String = ""

    override fun bntClicked() {
        message = model.loadMessage()
        activity.showText(message)
        Log.d(tag, "btnClicked")
    }

    override fun activityDestroyed() {
        Log.d(tag, "activityDestroyed()")
    }
}