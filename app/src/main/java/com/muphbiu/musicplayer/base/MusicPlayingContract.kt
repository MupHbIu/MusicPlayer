package com.muphbiu.musicplayer.base

interface MusicPlayingContract {
    interface View {
        fun showText(msg: String)
    }

    interface Presenter {
        fun bntClicked()
        fun onDestroy()
    }

    interface Repository {
        fun loadMessage(): String
    }
}