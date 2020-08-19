package com.muphbiu.musicplayer.base.fragments

import java.io.File

interface FragmentListInterface {
    fun itemSelected(item: File)
    fun getLocationToMainDir(): String
    fun showAddToPlayList(location: String)
    fun showFolder(folder: File)
    fun showFile(file: File)
}