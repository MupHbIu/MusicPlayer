package com.muphbiu.musicplayer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.muphbiu.musicplayer.ui.MusicPlayingActivity
import com.muphbiu.musicplayer.ui.PlayingListActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button2.setOnClickListener {
            startActivity(Intent(this, MusicPlayingActivity::class.java))
        }
        mainActB2.setOnClickListener {
            startActivity(Intent(this, PlayingListActivity::class.java))
        }
    }

}