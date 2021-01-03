package com.muphbiu.musicplayer.presenters

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import com.muphbiu.musicplayer.base.interfaces.PlayingServiceListener
import com.muphbiu.musicplayer.base.presenters.MusicPlayingPresenterInterface
import com.muphbiu.musicplayer.base.views.MusicPlayingViewInterface
import com.muphbiu.musicplayer.models.MusicPlayingModel
import com.muphbiu.musicplayer.services.PlayingService
import java.io.File

class MusicPlayingPresenter(
    private var activity: MusicPlayingViewInterface,
    private val context: Context
) :
    MusicPlayingPresenterInterface, PlayingServiceListener, ServiceConnection {

    private val tag: String = "MusicPlayingPresenter"

    private var model: MusicPlayingModel = MusicPlayingModel(context)
    private var playingService: PlayingService? = null
    private var working = false

    private var playlistPath = ""
    private var songPosition = 0
    private var newSong = false

    private lateinit var playlist: List<File>

    // ========== SET PARAMS ==========
    fun setPlaylist(playlistPath: String) {
        if(this.playlistPath != playlistPath)
        this.playlistPath = playlistPath
    }
    fun setItem(item: Int) {
        if(songPosition != item)
        songPosition = item
    }
    fun start(new: Boolean = true) {
        newSong = new
        playlist = model.getPlaylist(playlistPath)

        startService()
    }
    // ========== SET PARAMS ==========

    // ========== BIND ==========
    override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
        playingService = (binder as PlayingService.PlayingServiceBinder).getService()
        playingService!!.setListener(this)
        playingService!!.setPlaylistPath(playlistPath)
        playingService!!.setPlayingPlaylist(playlist)
        playingService!!.setSongPosition(songPosition)
        playingService!!.update(newSong)
    }
    override fun onServiceDisconnected(p0: ComponentName?) {
        Log.d("TAG", "Unbind")
    }
    // ========== BIND ==========

    // ========== TO SERVICE ==========
    private fun startService(action: Int = 0) {
        val intent = Intent(context, PlayingService::class.java).putExtra("LateAction", action)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }
        context.bindService(intent, this, 0)
    }
    private fun startServiceWithCurrentPosition(currentPosition: Int) {
        val intent = Intent(context, PlayingService::class.java)
            .putExtra("LateAction", 6)
            .putExtra("CurrentPosition", currentPosition)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }
        context.bindService(intent, this, 0)
    }
    fun playPause() {
        if(!working)
            startService(1)
        else
            playingService?.playPause()
    }
    fun playPrevious() {
        if(!working)
            startService(2)
        else
            playingService?.playPrevious()
    }
    fun playNext() {
        if(!working)
            startService(3)
        else
            playingService?.playNext()
    }
    fun changeRandom() {
        if(!working)
            startService(4)
        else {
            activity.showMessage("(Random) Not working now")
            playingService?.changeRandomMode()
            // TODO: Randomise playlist
        }
    }
    fun changeRepeat() {
        if(!working)
            startService(5)
        else
            playingService?.changeRepeatMode()
    }
    fun changeCurrentPosition(time: Int) {
        if(!working)
            startServiceWithCurrentPosition(time)
        else
            playingService?.setCurrentPosition(time)
    }
    fun getCurrentTime() {
        updateCurrentTime(playingService?.getCurrentPosition() ?: 0)
    }
    // ========== TO SERVICE ==========

    // ========== FROM SERVICE ==========
    override fun updateView() {
        val song: File = playingService!!.getPlaylist()[playingService!!.getSongPosition()]
        activity.setSongName(song.name)
        activity.setAuthor(File(playlistPath).name)
        activity.setAlbum(File(song.parent).name)
        activity.setSongDuration(playingService!!.getDuration())
        updateCurrentTime(playingService!!.getCurrentPosition())
        updatePlayPause(playingService!!.isPlaying())
        updateRepeatMode(playingService!!.getRepeatValue())
        updateRandomMode(playingService!!.getRandomValue())
    }
    override fun updateCurrentTime(time: Int) {
        activity.setCurrentTime(time)
        //playingService.updateCurrentPosition()
    }
    override fun updateRepeatMode(value: Int) {
        activity.setRepeatValue(value)
    }
    override fun updateRandomMode(value: Boolean) {
        activity.setRandomValue(value)
    }
    override fun updatePlayPause(value: Boolean) {
        activity.setPlayPauseValue(value)
    }
    // ========== FROM SERVICE ==========

    override fun setServiceStatus(status: Boolean) {
        working = status
    }

    // ========== FROM SERVICE ==========

    // ========== D E F A U L T ==========
    override fun activityDestroyed() {
        context.unbindService(this)
        Log.d(tag, "activityDestroyed()")
    }
}