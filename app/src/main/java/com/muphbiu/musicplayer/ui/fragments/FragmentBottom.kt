package com.muphbiu.musicplayer.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.muphbiu.musicplayer.R
import com.muphbiu.musicplayer.base.fragments.FragmentBottomInterface
import kotlinx.android.synthetic.main.fragment_bottom.view.*

class FragmentBottom() : Fragment() {
    private lateinit var activityI: FragmentBottomInterface
    val KEY_NAME = "SONG_NAME"
    val KEY_AUTHOR = "SONG_AUTHOR"
    val KEY_IS_PLAYING = "IS_PLAYING"

    fun setListener(activityI: FragmentBottomInterface) {
        this.activityI = activityI
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_bottom, container, false)
        val songName = arguments?.getString(KEY_NAME, "") ?: ""
        val songAuthor = arguments?.getString(KEY_AUTHOR, "") ?: ""
        val isPlaying = arguments?.getBoolean(KEY_IS_PLAYING, false) ?: false

        view.fragmentBottomSongName.text = songName
        view.fragmentBottomSongAuthor.text = songAuthor
        if(isPlaying)
            view.fragmentBottomPlayPause.setImageResource(R.drawable.player_pause)
        else
            view.fragmentBottomPlayPause.setImageResource(R.drawable.player_play_arrow)
        view.fragmentBottom.setOnClickListener { activityI.openPlayer() }
        view.fragmentBottomPrevious.setOnClickListener { activityI.playPrevious() }
        view.fragmentBottomPlayPause.setOnClickListener { activityI.playPause() }
        view.fragmentBottomNext.setOnClickListener { activityI.playNext() }

        return view
    }
}