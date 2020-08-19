package com.muphbiu.musicplayer.models

import android.content.Context
import com.muphbiu.musicplayer.base.models.FileExplorerModelInterface
import com.muphbiu.musicplayer.data.PlaylistsManager
import com.muphbiu.musicplayer.data.ReadSongsFromSD
import java.io.File

class FileExplorerModel (context: Context) : FileExplorerModelInterface{
    private val readFiles : ReadSongsFromSD = ReadSongsFromSD()
    private val playlistsManager = PlaylistsManager(context)

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
        return playlistsManager.getListOfPlaylist()
    }

    fun createNewPlaylistFile(playlistName: String) : String {
        return playlistsManager.createPlaylist(playlistName)
    }

    fun addSongToPlaylist(location: String, playlist: String) : Boolean {
        return if(File(location).isDirectory)
            playlistsManager.addListOfSongToPlaylist(readFiles.getSongsFromDirectory(File(location)), playlist)
        else
            playlistsManager.addSongToPlaylist(File(location), playlist)
    }
}
