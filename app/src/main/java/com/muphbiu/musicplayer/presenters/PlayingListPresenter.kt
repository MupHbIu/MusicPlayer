package com.muphbiu.musicplayer.presenters

import android.content.Context
import android.util.Log
import com.muphbiu.musicplayer.base.presenters.PlayingListPresenterInterface
import com.muphbiu.musicplayer.base.views.PlayingListViewInterface
import com.muphbiu.musicplayer.data.Song
import com.muphbiu.musicplayer.models.PlayingListModel

class PlayingListPresenter(View: PlayingListViewInterface, context: Context) : PlayingListPresenterInterface {
    private val tag = "PlayingListPresenter"

    private var activity: PlayingListViewInterface = View
    private var model: PlayingListModel = PlayingListModel(context)

    private var activitySongList = ""
    private var modelSongList: List<Song> = mutableListOf()

    override fun activityOpened() {
        modelSongList = model.loadPlayingList()
        activitySongList = modelSongList.toString()
        activity.showPlayingList(activitySongList)
    }

    override fun activityDestroyed() {
        Log.d(tag, "activityDestroyed()")
    }
}