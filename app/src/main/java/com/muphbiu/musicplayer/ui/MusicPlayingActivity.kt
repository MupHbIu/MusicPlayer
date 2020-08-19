package com.muphbiu.musicplayer.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.muphbiu.musicplayer.R
import com.muphbiu.musicplayer.base.views.MusicPlayingViewInterface
import com.muphbiu.musicplayer.presenters.MusicPlayingPresenter
import kotlinx.android.synthetic.main.activity_music_playing.*
import java.io.File


class MusicPlayingActivity : AppCompatActivity(),
    MusicPlayingViewInterface {
    private val tag: String = "MusicPlayingActivity"

    private lateinit var presenter: MusicPlayingPresenter

    private lateinit var songPlayingNow: File
    private lateinit var playlistNow: File

    private var random: Boolean = false
    private var repeat: Int = -1
    private var isPlaying = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_playing)



        presenter = MusicPlayingPresenter(this, this)

        playerSongNameTV.text = resources.getString(R.string.defStr)

        playerBack.setOnClickListener { onBackPressed() }
        playerPlaylist.setOnClickListener {
            startActivity(Intent(this, PlayingListActivity::class.java))
        }

        playerRandom.setOnClickListener {
            random = !random
            if(random)
                playerRandom.setImageResource(R.drawable.player_random_on)
            else
                playerRandom.setImageResource(R.drawable.player_random_off)
            showMessage("Random")
        }
        playerPrevious.setOnClickListener {
            showMessage("Previous")
        }
        playerPlayPause.setOnClickListener {
            if(isPlaying)
                playerPlayPause.setImageResource(R.drawable.player_play_arrow)
            else
                playerPlayPause.setImageResource(R.drawable.player_pause)
            isPlaying = !isPlaying
            presenter.bntClicked()
            showMessage("Play/pause")
        }
        playerNext.setOnClickListener {
            showMessage("Next")
        }
        playerRepeat.setOnClickListener {
            repeat++
            when(repeat) {
                0 -> playerRepeat.setImageResource(R.drawable.player_repeat_all)
                1 -> playerRepeat.setImageResource(R.drawable.player_repeat_one)
                else -> {
                    playerRepeat.setImageResource(R.drawable.player_repeat)
                    repeat = -1
                }
            }
            showMessage("Repeat")
        }

        Log.d(tag, "onCreate()")
    }

    override fun showText(msg: String) {
        playerSongNameTV.text = msg
    }



    override fun onDestroy() {
        super.onDestroy()
        presenter.activityDestroyed()
        Log.d(tag, "onDestroy")
    }

    override fun showLoad() {
        TODO("Show load")
    }

    override fun hideLoad() {
        TODO("Hide load")
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}