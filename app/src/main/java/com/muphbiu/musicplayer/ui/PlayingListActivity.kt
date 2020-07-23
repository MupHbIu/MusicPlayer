 package com.muphbiu.musicplayer.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.muphbiu.musicplayer.R
import com.muphbiu.musicplayer.base.presenters.PlayingListPresenterInterface
import com.muphbiu.musicplayer.base.views.PlayingListViewInterface
import com.muphbiu.musicplayer.data.database.PlayingListDB
import com.muphbiu.musicplayer.presenters.PlayingListPresenter
import kotlinx.android.synthetic.main.activity_playing_list.*

 class PlayingListActivity : AppCompatActivity(), PlayingListViewInterface {
     private val tag = "PlayingList"

     //private lateinit var playNow: PlayingListDB
     //private lateinit var songList: List<Song>
     private lateinit var presenter: PlayingListPresenterInterface

     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_playing_list)

         presenter = PlayingListPresenter(this, this)
         presenter.activityOpened()

         Log.d(tag, "onCreate()")

         /*
         playNow = PlayingListDB(this)
         playlistAdd.setOnClickListener {
            Log.d(tag, "ADD")
            playNow.addToDB(playlistName.text.toString(), playlistLocation.text.toString())
         }
         playlistDel.setOnClickListener {
             Log.d(tag, "DEL")
             playNow.deleteBd()
         }
         playlistGet.setOnClickListener {
             Log.d(tag, "GET")
             songList = playNow.getFromDB()
             playlistTV.text = songList.toString()
         }
         */

    }

     override fun showPlayingList(songList: String) {
         playlistTV.text = songList
     }

     override fun onResume() {
         super.onResume()
         presenter.activityOpened()
     }

     override fun onDestroy() {
         super.onDestroy()
         presenter.activityDestroyed()
         Log.d(tag, "onDestroy")
     }

 }