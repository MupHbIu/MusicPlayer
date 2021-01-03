package com.muphbiu.musicplayer.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.SeekBar
import android.widget.Toast
import com.muphbiu.musicplayer.R
import com.muphbiu.musicplayer.base.views.MusicPlayingViewInterface
import com.muphbiu.musicplayer.data.PlayerInstanceValues
import com.muphbiu.musicplayer.presenters.MusicPlayingPresenter
import com.muphbiu.musicplayer.ui.fragments.LoadDialogFragment
import kotlinx.android.synthetic.main.activity_music_playing.*
import java.util.*
import kotlin.concurrent.timer


class MusicPlayingActivity : AppCompatActivity(),
    MusicPlayingViewInterface, SeekBar.OnSeekBarChangeListener {
    private val tag: String = "MusicPlayingActivity"
    private lateinit var dialog: LoadDialogFragment

    private lateinit var presenter: MusicPlayingPresenter
    private var t: Timer = Timer()

    private var songPlayingNow: String = ""
    private var playlistNow: String = ""
    private var itemSelected = 0

    private var progressChanging = false
    private var moving = false
    private var startX = 0
    private var x = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_playing)
        presenter = MusicPlayingPresenter(this, this)

        songPlayingNow = intent.getStringExtra("SONG_PATH") ?: ""
        playlistNow = intent.getStringExtra("PLAYLIST") ?: ""
        itemSelected = intent.getIntExtra("ITEM", 0)
        if(playlistNow == "") {
            val sPref = getSharedPreferences(PlayerInstanceValues.KEY_PREFERENCES, Context.MODE_PRIVATE)
            playlistNow = sPref.getString(PlayerInstanceValues.PLAYING_PLAYLIST, "") ?: ""
            itemSelected = sPref.getInt(PlayerInstanceValues.SONG_POSITION, 0)
            presenter.setPlaylist(playlistNow)
            presenter.setItem(itemSelected)
            presenter.start(false)
        } else {
            presenter.setPlaylist(playlistNow)
            presenter.setItem(itemSelected)
            presenter.start()
        }

        playerBack.setOnClickListener { onBackPressed() }
        playerPlaylist.setOnClickListener {
            startActivity(Intent(this, PlayingListActivity::class.java))
        }

        playerRandom.setOnClickListener {
            presenter.changeRandom()
        }
        playerPrevious.setOnClickListener {
            presenter.playPrevious()
        }
        playerPlayPause.setOnClickListener {
            presenter.playPause()
        }
        playerNext.setOnClickListener {
            presenter.playNext()
        }
        playerRepeat.setOnClickListener {
            presenter.changeRepeat()
        }
        playerSongSeekbar.setOnSeekBarChangeListener(this)
        playerImageView.setOnTouchListener { view, event ->
            x = event.x.toInt()

            when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startX = x
                    moving = true
                }
                MotionEvent.ACTION_MOVE -> {
                    if(moving)
                        if(startX - x > view.width * 60 / 100) {
                            presenter.playNext()
                            moving = false
                        }
                        else if(-(startX - x) > view.width * 60 / 100) {
                            presenter.playPrevious()
                            moving = false
                        }
                }
            }
            true
        }


       t = timer(null, false, 1000, 1000) {
           runOnUiThread {
               presenter.getCurrentTime()
           }
        }
    }
    override fun onProgressChanged(seekBar: SeekBar, progress: Int, p2: Boolean) {
        //presenter.changeCurrentPosition(p1)
        playerSongSeekbar.progress = progress
        val durationMin = (progress / 1000) / 60
        val durationSec = (progress / 1000) % 60
        playerSongTime.text = "$durationMin:${if(durationSec>9) durationSec else "0$durationSec"}"
    }
    override fun onStartTrackingTouch(seekBar: SeekBar) {
        progressChanging = true
    }
    override fun onStopTrackingTouch(seekBar: SeekBar) {
        presenter.changeCurrentPosition(seekBar.progress)
        progressChanging = false
    }

    override fun setRandomValue(value: Boolean) {
        if(value)
            playerRandom.setImageResource(R.drawable.player_random_on)
        else
            playerRandom.setImageResource(R.drawable.player_random_off)
    }
    override fun setPlayPauseValue(value: Boolean) {
        if(value)
            playerPlayPause.setImageResource(R.drawable.player_pause)
        else
            playerPlayPause.setImageResource(R.drawable.player_play_arrow)
    }
    override fun setRepeatValue(value: Int) {
        when(value) {
            0 -> playerRepeat.setImageResource(R.drawable.player_repeat_all)
            1 -> playerRepeat.setImageResource(R.drawable.player_repeat_one)
            else -> playerRepeat.setImageResource(R.drawable.player_repeat)
        }
    }
    override fun setSongName(name: String) {
        playerSongNameTV.text = name
    }
    override fun setAuthor(author: String) {
        playerSongAutor.text = author
    }
    override fun setAlbum(album: String) {
        playerSongAlbum.text = album
    }
    override fun setCurrentTime(time: Int) {
        if(!progressChanging) {
            playerSongSeekbar.progress = time
            val durationMin = (time / 1000) / 60
            val durationSec = (time / 1000) % 60
            playerSongTime.text = "$durationMin:${if(durationSec>9) durationSec else "0$durationSec"}"
        }
    }
    override fun setSongDuration(duration: Int) {
        playerSongSeekbar.max = duration
        val durationMin = (duration / 1000) / 60
        val durationSec = (duration / 1000) % 60
        playerSongDuration.text = "$durationMin:${if(durationSec>9) durationSec else "0$durationSec"}"
    }
    override fun showText(msg: String) {
        playerSongNameTV.text = msg
    }

    // ========== D E F A U L T ==========
    override fun onDestroy() {
        presenter.activityDestroyed()
        //t.cancel()
        super.onDestroy()
    }

    override fun showLoad() {
        dialog = LoadDialogFragment()
        dialog.isCancelable = false
        dialog.show(supportFragmentManager, "LoadDialog")
    }

    override fun hideLoad() {
        dialog.dismiss()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}