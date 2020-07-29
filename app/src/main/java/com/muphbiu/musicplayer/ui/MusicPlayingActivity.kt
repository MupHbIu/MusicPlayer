package com.muphbiu.musicplayer.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.muphbiu.musicplayer.R
import com.muphbiu.musicplayer.base.views.MusicPlayingViewInterface
import com.muphbiu.musicplayer.presenters.MusicPlayingPresenter
import kotlinx.android.synthetic.main.activity_music_playing.*


class MusicPlayingActivity : AppCompatActivity(),
    MusicPlayingViewInterface {
    private val tag: String = "MusicPlayingActivity"

    private lateinit var presenter: MusicPlayingPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_playing)

        presenter = MusicPlayingPresenter(this, this)

        txt1.text = resources.getString(R.string.defStr)
        button.setOnClickListener {
            presenter.bntClicked()
        }
        listBtn.setOnClickListener {
            startActivity(Intent(this, PlayingListActivity::class.java))
        }

        Log.d(tag, "onCreate()")
    }

    override fun showText(msg: String) {
        txt1.text = msg
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

    override fun showMessage(Message: String) {
        TODO("Toast.makeText()")
    }
}