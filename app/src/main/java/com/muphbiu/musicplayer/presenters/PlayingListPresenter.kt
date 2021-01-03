package com.muphbiu.musicplayer.presenters

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import com.muphbiu.musicplayer.base.interfaces.PlayingServiceListener
import com.muphbiu.musicplayer.base.presenters.PlayingListPresenterInterface
import com.muphbiu.musicplayer.base.views.PlayingListViewInterface
import com.muphbiu.musicplayer.data.Song
import com.muphbiu.musicplayer.models.PlayingListModel
import com.muphbiu.musicplayer.services.PlayingService
import java.io.File

class PlayingListPresenter(private var activity: PlayingListViewInterface, private val context: Context) :
    PlayingListPresenterInterface, ServiceConnection {
    private val tag = "PlayingListPresenter"

    private var model: PlayingListModel = PlayingListModel(context)
    private var player: PlayingService? = null
    private var serviceStatus = false

    private var songList : List<Song> = mutableListOf()

    init {
        startService(0)
    }

    private fun startService(action: Int = 0) {
        val intent = Intent(context, PlayingService::class.java).putExtra("LateAction", action)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }
        context.bindService(intent, this, 0)
    }
    // ========== BIND ==========
    override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
        player = (binder as PlayingService.PlayingServiceBinder).getService()
        updateView()
    }
    override fun onServiceDisconnected(p0: ComponentName?) {
        Log.d("TAG", "Unbind")
        player = null
    }
    // ========== BIND ==========

    // ========== Service ==========
    private fun updateView() {
        val playlistPath = player!!.getPlaylistPath()
        activity.setPlaylistPath(playlistPath)
        activity.setPlaylist(model.getPlaylist(playlistPath))
        activity.updatePlaylist()
    }
    // ========== Service ==========

    // ========== D E F A U L T ==========
    override fun activityDestroyed() {
        context.unbindService(this)
    }
}