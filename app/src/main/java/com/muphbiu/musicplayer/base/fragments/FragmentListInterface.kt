package com.muphbiu.musicplayer.base.fragments

import java.io.File

interface FragmentListInterface {
    fun itemSelected(item: Int)
    fun getLocationToMainDir(): String
    fun showAddToPlayList(location: String)
    fun showFolder(folder: File)
    fun showFile(file: File)

    fun renamePlaylist(oldName: String, newName: String)
    fun deletePlaylist(name: String)
    fun showMessage(str: String)
}