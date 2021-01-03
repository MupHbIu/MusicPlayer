package com.muphbiu.musicplayer.base.interfaces

import java.io.File

interface FileExplorerAdapterListener {
    fun getLocationToMainDir() : String
    fun showAddToPlayList(location: String)
    fun showFolder(folder: File)
    fun showFile(file: File)
}