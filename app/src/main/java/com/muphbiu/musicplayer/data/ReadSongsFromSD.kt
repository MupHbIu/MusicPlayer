package com.muphbiu.musicplayer.data

import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.File
import java.lang.Exception

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ReadSongsFromSD() {
    private val tag = "ReadSongsFromSD"
    private var mainDir : File = Environment.getExternalStorageDirectory()
    private val ext = ".mp3"
    private val songsList = mutableListOf<Song>()

    init {
        findSongsLocation(Environment.getExternalStorageDirectory())
        var rawSecondaryStoragesStr: String? = System.getenv("ANDROID_STORAGE");
        if (rawSecondaryStoragesStr != null && rawSecondaryStoragesStr.isNotEmpty()) {
            /*var rawSecondaryStorages = rawSecondaryStoragesStr.split(File.pathSeparator);
            for (rawSecondaryStorage in rawSecondaryStorages) {
            }*/
            findSongsLocation(File(rawSecondaryStoragesStr))

        }
    }

    private fun findSongsLocation(dir: File) {
        try {
            for (it in dir.listFiles()) {
                if (it != null && !(it.name.toString().startsWith('.') || it.name.toString() == "Android") ) {
                    if (it.isDirectory) {
                        findSongsLocation(it)
                    } else if (it.name.toString().endsWith(ext)) {
                        songsList.add(Song(it.name.toString(), it.absolutePath.toString()))
                    }
                }
            }
        } catch (e: Exception) {
            Log.d(tag, e.toString())
        }
    }

    fun update() {
        findSongsLocation(mainDir)
    }

    fun getSoundsLocation() : List<Song> {
        return songsList
    }

    /*
    class FNameFilter(ext: String) : FilenameFilter {
        private val ext: String
        init {
            this.ext = ext.toLowerCase()
        }

        override fun accept(dir: File, name: String): Boolean {
            return name.toLowerCase().endsWith(ext)
        }
    }
*/
}