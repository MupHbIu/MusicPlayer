package com.muphbiu.musicplayer.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.muphbiu.musicplayer.R
import com.muphbiu.musicplayer.base.interfaces.PlayingServiceListener
import com.muphbiu.musicplayer.data.PlayerInstanceValues
import com.muphbiu.musicplayer.data.PlayerInstanceValues.Companion.KEY_PREFERENCES
import com.muphbiu.musicplayer.ui.MusicPlayingActivity
import kotlinx.coroutines.*
import java.io.File
import java.io.FileInputStream

class PlayingService : Service() {
    companion object {
        const val FOREGROUND_NOTIFICATION_ID = 1
        const val NOTIFICATION_GROUP_ID = "NotificationGroup1"
    }
    private val binder: PlayingServiceBinder = PlayingServiceBinder()
    private lateinit var notificationManager: NotificationManager
    private lateinit var notificationChannel: NotificationChannel
    private lateinit var notification: Notification
    private lateinit var remoteViews: RemoteViews
    private lateinit var notificationBuilder: NotificationCompat.Builder
    private var working = false

    private var listener: PlayingServiceListener? = null
    private lateinit var sPref: SharedPreferences
    private var player: MediaPlayer? = null
    private var initAction = 0

    private var randomMode = false
    private var repeatMode: Int = -1
    private var playlistPath = ""
    private var songPosition = 0
    private var currentPosition = 0

    private var playlist = mutableListOf<File>()

    // ========== B I N D ==========
    inner class PlayingServiceBinder : Binder() {
        fun getService() : PlayingService {
            return this@PlayingService
        }
    }
    override fun onBind(intent: Intent): IBinder {
        Log.d("TAG", "OnBind")
        return binder
    }
    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
    }
    override fun onUnbind(intent: Intent?): Boolean {
        //return super.onUnbind(intent)
        return true
    }
    // ========== B I N D ==========

    // ========== LIFE CYCLE ==========
    override fun onCreate() {
        super.onCreate()
        Log.d("TAG", "OnCreate")
        sPref = getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE)
        loadInstance()

        notificationManager = getSystemService(NotificationManager::class.java) as NotificationManager
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if(!working) {
            val intent = Intent(this, MusicPlayingActivity::class.java)
            val pi = PendingIntent.getActivity(this, 0, intent, 0)

            val i = Intent(this, PlayingReceiver::class.java)

            remoteViews = RemoteViews(packageName, R.layout.notification_player)
            remoteViews.setTextViewText(R.id.notificationSong, "Some Song")
            remoteViews.setTextViewText(R.id.notificationAuthor, "Some Author")
            remoteViews.setInt(R.id.notificationImage, "setImageResource", R.mipmap.ic_launcher)
            remoteViews.setOnClickPendingIntent(R.id.notificationPrevious, PendingIntent.getBroadcast(this, 0, i.setAction("Previous"), 0))
            remoteViews.setOnClickPendingIntent(R.id.notificationPlayPause, PendingIntent.getBroadcast(this, 0, i.setAction("PlayPause"), 0))
            remoteViews.setOnClickPendingIntent(R.id.notificationNext, PendingIntent.getBroadcast(this, 0, i.setAction("Next"), 0))
            remoteViews.setOnClickPendingIntent(R.id.notificationStop, PendingIntent.getBroadcast(this, 0, i.setAction("Stop"), 0))
            remoteViews.setOnClickPendingIntent(R.id.notificationLayout, pi)

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannelGroup(NotificationChannelGroup(NOTIFICATION_GROUP_ID, "Group 1"))
                notificationChannel = NotificationChannel("CHANNEL_ID", "Notification channel", NotificationManager.IMPORTANCE_NONE)
                notificationChannel.description = "Description"
                notificationChannel.enableVibration(false)
                notificationChannel.setSound(null, null)
                notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                notificationChannel.group = NOTIFICATION_GROUP_ID
                notificationManager.createNotificationChannel(notificationChannel)
            }

            notificationBuilder = NotificationCompat.Builder(this, "CHANNEL_ID")
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCategory(NotificationCompat.CATEGORY_SERVICE)
                .setContent(remoteViews)
                .setGroup(NOTIFICATION_GROUP_ID)
                .setDefaults(0)
            notification = notificationBuilder.build()
            startForeground(FOREGROUND_NOTIFICATION_ID, notification)
            working = true
        }

        initAction = intent?.getIntExtra("LateAction", 0) ?: 0
        if(initAction == 6)
            currentPosition = intent?.getIntExtra("CurrentPosition", 0) ?: 0
        when(intent?.getIntExtra("ACTION", 0)) {
            1 -> playPrevious()
            2 -> playPause()
            3 -> playNext()
            4 -> close()
        }


        return super.onStartCommand(intent, flags, startId)
    }
    override fun onDestroy() {
        //saveInstance()

        listener?.setServiceStatus(false)
        super.onDestroy()
    }
    override fun onTaskRemoved(rootIntent: Intent?) {
        saveInstance()
        listener?.setServiceStatus(false)
        super.onTaskRemoved(rootIntent)
    }
    // ========== LIFE CYCLE ==========

    // ========== I N S T A N C E ==========
    private fun saveInstance() {
        PlayerInstanceValues(this).savePlayingPlaylist(getPlaylistPath())
        PlayerInstanceValues(this).saveSongPosition(getSongPosition())
        PlayerInstanceValues(this).saveCurrentTime(getCurrentPosition())
        PlayerInstanceValues(this).saveRandom(getRandomValue())
        PlayerInstanceValues(this).saveRepeat(getRepeatValue())
        PlayerInstanceValues(this).savePlaying(isPlaying())
    }
    private fun loadInstance() {
        playlistPath = PlayerInstanceValues(this).getPlayingPlaylist()
        songPosition = PlayerInstanceValues(this).getSongPosition()
        currentPosition = PlayerInstanceValues(this).getCurrentTime()
        randomMode = PlayerInstanceValues(this).getRandom()
        repeatMode = PlayerInstanceValues(this).getRepeat()
    }
    private fun newPlayer(new: Boolean) {
        GlobalScope.async(Dispatchers.Unconfined) {
            val fd = FileInputStream(playlist[songPosition].absolutePath).fd
            player = MediaPlayer()
            player?.setDataSource(fd)
            player?.setAudioStreamType(AudioManager.STREAM_MUSIC)
            player?.setOnPreparedListener { if(working) onPrepared(new) }
            player?.setOnCompletionListener { if(working) complete() }
            player?.prepare()
        }
    }

    fun setListener(listener: PlayingServiceListener) { this.listener = listener; this.listener?.setServiceStatus(true) }
    fun setPlaylistPath(path: String) { playlistPath = path }
    fun setPlayingPlaylist(playlist: List<File>) { this.playlist = playlist.toMutableList() }
    fun setSongPosition(position: Int) { songPosition = position }
    fun update(new: Boolean = true) {
        if(player == null && new) {
            currentPosition = 0
            newPlayer(new)
        } else if(player == null && !new) {
            newPlayer(new)
        } else if(player != null && new) {
            player?.release()
            currentPosition = 0
            newPlayer(new)
        }
        listener?.updateView()
    }
    private fun onPrepared(play: Boolean = false) {
        // saveInstance()
        setCurrentPosition(currentPosition)
        if(play)
            play()
        if(initAction != 0) {
            when(initAction) {
                1 -> playPause()
                2 -> playPrevious()
                3 -> playNext()
                4 -> changeRandomMode()
                5 -> changeRepeatMode()
                6 -> setCurrentPosition(currentPosition)
            }
            initAction = 0
        }

        listener?.updateView()
        updateNotification()
    }
    private fun complete() {
        when (repeatMode) {
            1 -> playAgain()
            0 -> {
                if (songPosition == playlist.lastIndex)
                    songPosition = -1
                playNext()
            }
            else -> {
                if(songPosition == playlist.lastIndex) {
                    songPosition = 0
                    setCurrentPosition(0)
                    update(false)
                } else
                    playNext()
            }
        }
    }

    // Update notification
    private fun updateNotification() {
        remoteViews.setTextViewText(R.id.notificationSong, playlist[songPosition].name)
        remoteViews.setTextViewText(R.id.notificationAuthor, File(playlistPath).name)
        remoteViews.setInt(R.id.notificationImage, "setImageResource", R.mipmap.ic_launcher)

        if(isPlaying())
            remoteViews.setInt(R.id.notificationPlayPause, "setImageResource", R.drawable.btn_notification_pause)
        else
            remoteViews.setInt(R.id.notificationPlayPause, "setImageResource", R.drawable.btn_notification_play)

        notificationBuilder.setContent(remoteViews)
        notification = notificationBuilder.build()
        notificationManager.notify(FOREGROUND_NOTIFICATION_ID, notification)
    }

    // Stop service
    fun close() {
        pause(false)
        listener?.setServiceStatus(false)
        stopForeground(true)
        working = false
        saveInstance()
        stopSelf()
    }
    // ========== I N S T A N C E ==========

    // ========== S E T ==========
    fun playPause() {
        if(isPlaying())
            pause()
        else
            play()
    }
    fun play(showNotification: Boolean = true) {
        player?.start()
        listener?.updatePlayPause(player!!.isPlaying)
        if(showNotification)
            updateNotification()
    }
    fun pause(showNotification: Boolean = true) {
        player?.pause()
        listener?.updatePlayPause(player!!.isPlaying)
        if(showNotification)
            updateNotification()
    }
    fun stop(showNotification: Boolean = true) {
        player?.stop()
        listener?.updatePlayPause(player!!.isPlaying)
        if(showNotification)
            updateNotification()
    }
    fun setCurrentPosition(position: Int) {
        player?.seekTo(position)
    }
    fun playPrevious() {
        stop()
        if(songPosition == 0)
            songPosition = playlist.lastIndex
        else
            songPosition--
        update()
        setCurrentPosition(0)
    }
    fun playNext() {
        if(songPosition == playlist.lastIndex)
            songPosition = 0
        else
            songPosition++
        stop()
        update()
        setCurrentPosition(0)
    }
    private fun playAgain() {
        pause()
        setCurrentPosition(0)
        play()
    }
    fun changeRepeatMode() {
        if(repeatMode == 1)
            repeatMode = -1
        else
            repeatMode++
        listener?.updateRepeatMode(repeatMode)
    }
    fun changeRandomMode() {
        randomMode = !randomMode
        listener?.updateRandomMode(randomMode)
    }
    // ========== S E T ==========

    // ========== G E T ==========
    fun getPlaylistPath() : String {
        return playlistPath
    }
    fun getPlaylist() : List<File> {
        return playlist
    }
    fun getSongPosition() : Int {
        return songPosition
    }
    fun getCurrentPosition() : Int {
        return player!!.currentPosition
    }
    fun getDuration() : Int {
        return player!!.duration
    }
    fun isPlaying() : Boolean {
        return player!!.isPlaying
    }
    fun getRandomValue() : Boolean {
        return randomMode
    }
    fun getRepeatValue() : Int {
        return repeatMode
    }
}