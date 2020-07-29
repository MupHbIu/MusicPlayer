 package com.muphbiu.musicplayer.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.muphbiu.musicplayer.R
import com.muphbiu.musicplayer.base.presenters.PlayingListPresenterInterface
import com.muphbiu.musicplayer.base.views.PlayingListViewInterface
import com.muphbiu.musicplayer.data.Song
import com.muphbiu.musicplayer.presenters.PlayingListPresenter
import kotlinx.android.synthetic.main.activity_playing_list.*

 class PlayingListActivity : AppCompatActivity(), PlayingListViewInterface {
     private val tag = "PlayingListActivity"

     private lateinit var presenter: PlayingListPresenterInterface

     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_playing_list)

         presenter = PlayingListPresenter(this, this)

         playlistGet.setOnClickListener {
             //presenter.refresh()
         }

     }
     override fun onResume() {
         super.onResume()
         presenter.activityOpened()
     }

     override fun showPlayingList(SongList: List<Song>) {
         var songListStr : String = SongList.size.toString() + "\n"
         for (it in SongList) {
             songListStr += "${it.name.toString()} (${it.location.toString()}) \n"
         }
         playlistTV.text = songListStr
     }

     override fun onDestroy() {
         super.onDestroy()
         presenter.activityDestroyed()
     }
     override fun showMessage(Message: String) {
         Toast.makeText(this, Message, Toast.LENGTH_SHORT).show()
     }
     override fun showLoad() {
         TODO("Show load")
     }
     override fun hideLoad() {
         TODO("Show load")
     }
 }