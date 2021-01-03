package com.muphbiu.musicplayer.data

import android.content.Context
import java.io.*

class PlaylistsManager(private val context: Context) {

    val DEFAULT_DIR_PATH : String
    val FILE_ALLSONGS_NAME = "_AllSongs"
    val PLAYLIST_PLAYING_NOW = "_PlayingNow"

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

    fun getPlaylist(playlistName: String) : List<File> {
        val listOfSongs = mutableListOf<File>()
        if(playlistName != "$DEFAULT_DIR_PATH/$FILE_ALLSONGS_NAME") {
            val file = readFile(playlistName)
            if(file != emptyList<String>())
                for(line in file)
                    listOfSongs.add(File(line.substringAfter(':')))
        }
        return listOfSongs
    }
    fun getPlaylistPlayingNow() : List<File> {
        var list = getPlaylist("$DEFAULT_DIR_PATH/$PLAYLIST_PLAYING_NOW")
        if(list == emptyList<File>())
            list = getPlaylist("$DEFAULT_DIR_PATH/$FILE_ALLSONGS_NAME")
        return list
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
    fun renamePlaylist(playlistNameOld: String, playlistNameNew: String) : Boolean {
        return if (!(playlistNameNew == FILE_ALLSONGS_NAME || playlistNameNew == PLAYLIST_PLAYING_NOW)) {
            val oldFileData = readFile(playlistNameOld)
            var oldFileDataString = ""
            for(str in oldFileData)
                oldFileDataString += "$str\n"
            writeFile("${DEFAULT_DIR_PATH}/$playlistNameNew", oldFileDataString)
            deletePlaylist(playlistNameOld)
            true
        } else false
    }
    fun deletePlaylist(playlistName: String) {
        if (playlistName != FILE_ALLSONGS_NAME)
            if(playlistName == PLAYLIST_PLAYING_NOW) {
                writeFile(playlistName, "")
            } else
                deleteFile(context, playlistName)
    }

    private fun readFile(playlistName: String) : List<String> {
        var fileStr: List<String> = mutableListOf()
        try {
            val reader = FileReader("${DEFAULT_DIR_PATH}/$playlistName")
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