package com.muphbiu.musicplayer.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import java.io.*

class RawSongsFile(context: Context) {
    private val tag = "RawSongsFile"
    private val fileName =  "AllSongs"
    private lateinit var songsFromSD: ReadSongsFromSD
    private lateinit var newSongList: List<Song>

    lateinit var fos : FileOutputStream
    lateinit var fis : FileInputStream

    init {
        try {
            tryInit(context)
        } catch (e: FileNotFoundException) {
            writeFile(context, "")
            tryInit(context)
        }
    }
    private fun tryInit(context: Context) {
        if (getSongsList(context) == emptyList<Song>() || getSongsList(context) == null) {
            addSongsToFile(context)
        }
    }

    fun getSong(context: Context, id : String) : Song {
        val file: List<String> = readFile(context)
        var songName = ""
        var songLocation = ""
        if (file != emptyList<Song>()) {
            for (line in file) {
                if (line.substringBefore(':') == id) {
                    songName = line.substringAfter(":").substringBefore(';')
                    songLocation = line.substringAfter(';')
                    break
                }
            }
        }
        return Song(songName, songLocation)
    }
    fun getSongsList(context: Context) : List<Song> {
        val songsList = mutableListOf<Song>()
        val file: List<String> = readFile(context)
        if (file != emptyList<Song>()) {
            for (line in file) {
                val songName: String = line.substringAfter(":").substringBefore(';')
                val songLocation: String = line.substringAfter(';')
                songsList.add(Song(songName, songLocation))
            }
        }
        return songsList
    }
    private fun readFile(context: Context): List<String> {
        fis = context.openFileInput(fileName)
        var fileStr: List<String> = mutableListOf()
        try {
            val br = BufferedReader(InputStreamReader(fis))
            // читаем содержимое
           fileStr = br.readLines()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return fileStr
    }

    fun addSongsToFile(context: Context) {
        songsFromSD = ReadSongsFromSD()
        newSongList = songsFromSD.getSoundsLocation()
        var file = ""
        for ((i, song) in newSongList.withIndex()) {
            file += "${i.toString()}:${song.name.toString()};${song.location.toString()}\n"
        }
        writeFile(context, file)
    }
    private fun writeFile(context: Context, string: String) {
        try {
            fos = context.openFileOutput(fileName, MODE_PRIVATE)
            val bw = BufferedWriter(OutputStreamWriter(fos))
            bw.write(string)
            bw.close()
            Log.d(tag, "Файл записан")
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun deleteFile(context: Context) {
        try {
            fos = context.openFileOutput(fileName, MODE_PRIVATE)
            val bw = BufferedWriter(OutputStreamWriter(fos))
            bw.write("")
            bw.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


}