package com.muphbiu.musicplayer.presenters

import android.content.Context
import com.muphbiu.musicplayer.base.presenters.PlaylistPresenterInterface
import com.muphbiu.musicplayer.base.views.PlaylistActivityViewInterface
import com.muphbiu.musicplayer.models.PlaylistModel


class PlaylistPresenter(private val activity: PlaylistActivityViewInterface, context: Context) : PlaylistPresenterInterface {

    private val model = PlaylistModel(context)

    fun getData(playlistPath: String) {
        val listPlaylist = if(playlistPath != "")
             model.getPlaylist(playlistPath)
        else
            model.getListOfPlaylist()
        activity.updatePlaylist(listPlaylist)
        activity.updateView()
    }

    // ========== D E F A U L T ==========
    override fun activityDestroyed() {
        
    }

}