package com.muphbiu.musicplayer.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.muphbiu.musicplayer.R
import com.muphbiu.musicplayer.base.MusicPlayingContract
import com.muphbiu.musicplayer.presenters.MusicPlayingPresenter
import kotlinx.android.synthetic.main.activity_music_playing.*


class MusicPlayingActivity : AppCompatActivity(), MusicPlayingContract.View {
    private val TAG: String = "MusicPlayingActivity"

    private var MusicPlayingPresenter: MusicPlayingContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_playing)

        txt1.text = "Default Text"
        button.setOnClickListener {
            MusicPlayingPresenter?.bntClicked() }
        MusicPlayingPresenter = MusicPlayingPresenter(this)

        Log.d(TAG, "onCreate()")
    }

    override fun showText(msg: String) {
        txt1.text = msg
    }

    override fun onDestroy() {
        super.onDestroy()
        MusicPlayingPresenter?.onDestroy()
        Log.d(TAG, "onDestroy")
    }
}