package com.muphbiu.musicplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.muphbiu.musicplayer.data.RawSongsFile
import com.muphbiu.musicplayer.data.database.PlayingListDB
import kotlinx.android.synthetic.main.activity_text1.*

class TestActivity : AppCompatActivity() {

    //private lateinit var songsDB : RawSongsDB
    private lateinit var songsFile : RawSongsFile
    private lateinit var playingListDB: PlayingListDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text1)

        //songsDB = RawSongsDB(this)
        songsFile = RawSongsFile(this)
        playingListDB = PlayingListDB(this)

        btnAdd.setOnClickListener {
            val param = editTextIndex.text.toString()
            if(param != "") {
                val song = songsFile.getSong(this, param)
                if(!(song.name == "" && song.location == "")) {
                    playingListDB.addToDB(song)
                    playList.text = "${song.name} added to playlist."
                } else
                    playList.text = "Song not added to playlist."
            } else
                playList.text = "Bad param."

        }
        btnDelete.setOnClickListener {
            playingListDB.deleteBd()
        }
    }

}