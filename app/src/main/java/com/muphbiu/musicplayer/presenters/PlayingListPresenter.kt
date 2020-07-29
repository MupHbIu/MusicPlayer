package com.muphbiu.musicplayer.presenters

import android.content.Context
import com.muphbiu.musicplayer.base.presenters.PlayingListPresenterInterface
import com.muphbiu.musicplayer.base.views.PlayingListViewInterface
import com.muphbiu.musicplayer.data.Song
import com.muphbiu.musicplayer.models.PlayingListModel

class PlayingListPresenter(View: PlayingListViewInterface, context: Context) : PlayingListPresenterInterface {
    private val tag = "PlayingListPresenter"

    private var activity: PlayingListViewInterface = View
    private var model: PlayingListModel = PlayingListModel(context)
    //private var model2: StoreSongsDB = StoreSongsDB(context)

    private var songList : List<Song> = mutableListOf()
    //private var modelSongList: List<Song> = mutableListOf()

    override fun activityOpened() {
        songList = model.loadPlayingList()
        if(songList != emptyList<Song>())
            activity.showPlayingList(songList)
        else
            activity.showMessage("No songs")
    }

    override fun activityDestroyed() {

    }
}