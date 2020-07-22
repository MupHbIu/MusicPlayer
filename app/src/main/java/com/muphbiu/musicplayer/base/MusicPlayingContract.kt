package com.muphbiu.musicplayer.base

public interface MusicPlayingContract {
    interface View {
        fun showText(msg: String)
    }

    interface Presenter {
        fun bntClicked()
        fun onDestroy()
    }

    interface Repository {

    }
}