package com.muphbiu.musicplayer.data

import android.os.Environment
import android.util.Log
import java.io.File
import java.lang.Exception

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ReadSongsFromSD {
    private val tag = "ReadSongsFromSD"
    private val MAIN_DIR = Environment.getExternalStorageDirectory().absolutePath
    private val SECOND_DIR = System.getenv("ANDROID_STORAGE")
    private val ext = ".mp3"
    private var songsList = mutableListOf<File>()

   fun getSoundsLocation() : List<File> {
       songsList = mutableListOf()
        findSongsLocation(File(MAIN_DIR))
        if (SECOND_DIR != null && SECOND_DIR.isNotEmpty()) {
            findSongsLocation(File(SECOND_DIR))
        }
        return songsList
    }
    fun getSongsFromDirectory(dir: File) : List<File> {
        songsList = mutableListOf()
        findSongsLocation(dir)
        return songsList
    }
    private fun findSongsLocation(dir: File) {
        try {
            for (it in dir.listFiles()) {
                if (it != null && !(it.name.toString().startsWith('.') || it.name.toString() == "Android") ) {
                    if (it.isDirectory) {
                        findSongsLocation(it)
                    } else if (it.name.toString().endsWith(ext)) {
                        songsList.add(it)
                    }
                }
            }
        } catch (e: Exception) { Log.d(tag, e.toString()) }
    }

    fun getDirItems(dir: File) : List<File> {
        val files = mutableListOf<File>()
        try {
            for(it in dir.listFiles()) {
                if(!(it.name.toString().startsWith('.') || it.name.toString() == "Android") &&
                    ((it.isDirectory && isContainMusic(it)) || (!it.isDirectory && it.name.toString().endsWith(ext)))
                )
                    files.add(File(it.absolutePath))
            }
        } catch (e: Exception) { Log.d(tag, e.toString()) }
        files.sortWith(compareBy( { !it.isDirectory }, { it.name } ))
        return files
    }
    fun isContainMusic(folder: File) : Boolean {
        var result = false
        try {
            for(it in folder.listFiles()) {
                if(!it.name.startsWith('.')) {
                    if((it.isDirectory && isContainMusic(File(it.absolutePath))) || it.name.toString().endsWith(ext))
                        result = true
                }
            }
        } catch (e: Exception) { Log.d(tag, e.toString()) }
        return result
    }

    fun getMainDir(): String {
        return MAIN_DIR.toString()
    }
    fun getSecondDir(): String {
        return SECOND_DIR.toString()
    }
}