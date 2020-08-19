package com.muphbiu.musicplayer.base.interfaces

import java.io.File

interface PlaylistAdapterListener {
    fun itemSelected(item: File)
}