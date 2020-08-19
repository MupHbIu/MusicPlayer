package com.muphbiu.musicplayer.localTest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.muphbiu.musicplayer.R
import com.muphbiu.musicplayer.data.PlaylistsManager
import com.muphbiu.musicplayer.data.RawSongsFile
import com.muphbiu.musicplayer.data.database.PlayingListDB
import kotlinx.android.synthetic.main.activity_text1.*
import java.io.File

class TestActivity : AppCompatActivity() {

    //private lateinit var songsDB : RawSongsDB
    //private lateinit var songsFile : RawSongsFile
    //private lateinit var playingListDB: PlayingListDB
    private lateinit var plm: PlaylistsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text1)

        //songsDB = RawSongsDB(this)
        //songsFile = RawSongsFile(this)
        //playingListDB = PlayingListDB(this)
        plm = PlaylistsManager(this)

        btnAdd.setOnClickListener {
            if(editTextIndex.text.toString() == "") {
                val playlists = plm.getListOfPlaylist()
                playList.text = ""
                for(playlist in playlists) {
                    playList.text = playList.text.toString() + "${playlist.name}\n"
                }
            } else {
                val songs = plm.getPlaylist(editTextIndex.text.toString())
                playList.text = ""
                if(songs != emptyList<File>())
                    for(song in songs) {
                        playList.text = playList.text.toString() + "${song.name}\n"
                    }
            }

            //old
            /*val param = editTextIndex.text.toString()
            if(param != "") {
                val song = songsFile.getSong(this, param)
                if(!(song.name == "" && song.location == "")) {
                    playingListDB.addToDB(song)
                    playList.text = "${song.name} added to playlist."
                } else
                    playList.text = "Song not added to playlist."
            } else
                playList.text = "Bad param."*/
        }
        btnDelete.setOnClickListener {
            plm.deletePlaylist(editTextIndex.text.toString())
            //delete BD
            //playingListDB.deleteBd()

        }
    }

}