package com.muphbiu.musicplayer.data

import android.content.Context
import java.io.*

class PlaylistsManager(private val context: Context) {

    private val DEFAULT_DIR_PATH : String
    private val FILE_ALLSONGS_NAME = "_AllSongs"
    private val PLAYLIST_PLAYING_NOW = "_PlayingNow"

    init {
        DEFAULT_DIR_PATH = context.filesDir.toString()
    }

    fun getAllListOfPlaylist() : List<File> {
        return File(DEFAULT_DIR_PATH).listFiles().toMutableList()
    }
    fun getListOfPlaylist() : List<File> {
        val playlists = getAllListOfPlaylist().toMutableList()
        playlists.removeAll {it.name.toString() == FILE_ALLSONGS_NAME}
        return playlists
    }

    fun getPlaylist(playlistPath: String) : List<File> {
        val listOfSongs = mutableListOf<File>()
        if(playlistPath != "$DEFAULT_DIR_PATH/$FILE_ALLSONGS_NAME") {
            val file = readFile(playlistPath)
            if(file != emptyList<String>())
                for(line in file)
                    listOfSongs.add(File(line.substringAfter(':')))
        }
        return listOfSongs
    }
    fun getPlaylistSize(playlistPath: String) : Int{
        return getPlaylist(playlistPath).size
    }

    fun addListOfSongToPlaylist(files: List<File>, playlistPath: String) : Boolean {
        for (it in files)
            addSongToPlaylist(it, playlistPath)
        return true
    }
    fun addSongToPlaylist(file: File, playlistPath: String) : Boolean {
        return if(!(playlistPath == "$DEFAULT_DIR_PATH/$FILE_ALLSONGS_NAME" || playlistPath == "$DEFAULT_DIR_PATH/$PLAYLIST_PLAYING_NOW")) {
            val data = "${getPlaylistSize(playlistPath)};${file.name}:${file.absolutePath}\n"
            writeFile(playlistPath, data)
        } else false
    }

    fun createPlaylist(playlistName: String) : String {
        return if (!(playlistName == FILE_ALLSONGS_NAME || playlistName == PLAYLIST_PLAYING_NOW)) {
            if (writeFile(File("${DEFAULT_DIR_PATH}/$playlistName").absolutePath, ""))
                "${context.filesDir}/$playlistName"
            else
                ""
        } else return ""
    }
    fun deletePlaylist(playlistPath: String) {
        if (playlistPath != "$DEFAULT_DIR_PATH/$FILE_ALLSONGS_NAME")
            if(playlistPath == "$DEFAULT_DIR_PATH/$PLAYLIST_PLAYING_NOW")
                writeFile(playlistPath, "")
            else
                deleteFile(context, playlistPath)
    }

    private fun readFile(playlistPath: String) : List<String> {
        var fileStr: List<String> = mutableListOf()
        try {
            val reader = FileReader("${DEFAULT_DIR_PATH}/$playlistPath")
            val br = BufferedReader(reader)
            fileStr = br.readLines()
            br.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return fileStr
    }
    private fun writeFile(playlistPath: String, data: String) : Boolean {
        return try {
            val writer = FileWriter(playlistPath, true)
            val bw = BufferedWriter(writer)
            bw.write(data)
            bw.close()
            true
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            false
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }
    private fun deleteFile(context: Context, playlistPath: String) {
        context.deleteFile(File(playlistPath).name)
    }
}