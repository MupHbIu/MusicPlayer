package com.muphbiu.musicplayer.localTest

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Log
import com.google.android.exoplayer2.util.Util
import com.muphbiu.musicplayer.R
import kotlinx.android.synthetic.main.activity_player.*


class PlayerActivity : AppCompatActivity() {
    private lateinit var player : SimpleExoPlayer

    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition: Long = 0
    private lateinit var playbackStateListener : PlaybackStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        playbackStateListener =
            PlaybackStateListener()
    }

    override fun onStart() {
        super.onStart()
        if(Util.SDK_INT >= 24)
            initializePlayer()
    }

    override fun onResume() {
        super.onResume()
        hideSystemUi()
        if(Util.SDK_INT < 24)
            initializePlayer()
    }

    override fun onPause() {
        super.onPause()
        if(Util.SDK_INT < 24)
            releasePlayer()
    }

    override fun onStop() {
        super.onStop()
        if(Util.SDK_INT >= 24)
            releasePlayer()
    }

    private fun releasePlayer() {
        if(player != null) {
            playWhenReady = player.playWhenReady
            playbackPosition = player.currentPosition
            currentWindow = player.currentWindowIndex
            player.removeListener(playbackStateListener)
            player.release()
        }
    }

    private fun hideSystemUi() {
        playerView.setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LOW_PROFILE
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        )
    }

    private fun initializePlayer() {
        player = SimpleExoPlayer.Builder(this).build()
        playerView.player = player
        //val uri = Uri.parse("https://storage.googleapis.com/exoplayer-test-media-0/play.mp3")
        val uri = Uri.parse("https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4")
        var mediaSource = buildMediaSource(uri)
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        player.prepare(mediaSource, false, false);

        player.addListener(playbackStateListener)
        player.prepare(mediaSource, false, false)
    }

    private fun buildMediaSource(uri: Uri) : MediaSource {
        var dataSoureFactory : DataSource.Factory = DefaultDataSourceFactory(this, Util.getUserAgent(this, this.applicationInfo.name))
        var mediaSourceFactory : ProgressiveMediaSource.Factory = ProgressiveMediaSource.Factory(dataSoureFactory)
        var mediaSource1 : MediaSource = mediaSourceFactory.createMediaSource(uri)
        var audioUri : Uri = Uri.parse("https://storage.googleapis.com/exoplayer-test-media-0/play.mp3")
        var mediaSource2 : MediaSource = mediaSourceFactory.createMediaSource(audioUri)

        return ConcatenatingMediaSource(mediaSource1, mediaSource2)
    }
}

private class PlaybackStateListener : Player.EventListener {
    private val TAG = PlayerActivity::class.java.name

    override fun onPlayerStateChanged(
        playWhenReady: Boolean,
        playbackState: Int
    ) {
        val stateString: String
        stateString = when (playbackState) {
            ExoPlayer.STATE_IDLE -> "ExoPlayer.STATE_IDLE      -"
            ExoPlayer.STATE_BUFFERING -> "ExoPlayer.STATE_BUFFERING -"
            ExoPlayer.STATE_READY -> "ExoPlayer.STATE_READY     -"
            ExoPlayer.STATE_ENDED -> "ExoPlayer.STATE_ENDED     -"
            else -> "UNKNOWN_STATE             -"
        }
        Log.d(
            TAG, "changed state to " + stateString
                    + " playWhenReady: " + playWhenReady
        )
    }
}