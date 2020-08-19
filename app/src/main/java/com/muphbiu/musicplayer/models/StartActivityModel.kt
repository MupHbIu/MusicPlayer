package com.muphbiu.musicplayer.models

import android.content.Context
import com.muphbiu.musicplayer.base.presenters.StartActivityPresenterInterface
import com.muphbiu.musicplayer.data.PlaylistsManager
import com.muphbiu.musicplayer.data.ReadSongsFromSD
import java.io.File

class StartActivityModel(private val presenter: StartActivityPresenterInterface, context: Context) {
    private val readFiles : ReadSongsFromSD = ReadSongsFromSD()
    private val manager = PlaylistsManager(context)

    // ======= Playlists ==========
    fun getPlaylist(path: String) : List<File> {
        return manager.getPlaylist(path)
    }
    fun getListOfPlaylist() : List<File> {
        return manager.getListOfPlaylist()
    }
    // ======= Playlists ==========

    // ======= Files ==========
    fun getFiles(location: String) : List<File> {
        return readFiles.getDirItems(File(location))
    }
    fun getFiles(file: File) : List<File> {
        return readFiles.getDirItems(file)
    }
    fun haveMusic(location: String) : Boolean{
        return readFiles.isContainMusic(File(location))
    }
    fun getDir(mainDir: Boolean) : String {
        return if(mainDir)
            readFiles.getMainDir()
        else
            readFiles.getSecondDir()
    }
    fun getPlaylists() : List<File> {
        return manager.getListOfPlaylist()
    }

    fun createNewPlaylistFile(playlistName: String) : String {
        return manager.createPlaylist(playlistName)
    }

    fun addSongToPlaylist(location: String, playlist: String) : Boolean {
        return if(File(location).isDirectory)
            manager.addListOfSongToPlaylist(readFiles.getSongsFromDirectory(File(location)), playlist)
        else
            manager.addSongToPlaylist(File(location), playlist)
    }
    // ======= Files ==========

}