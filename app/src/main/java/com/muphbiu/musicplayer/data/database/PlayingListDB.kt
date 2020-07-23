package com.muphbiu.musicplayer.data.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.muphbiu.musicplayer.data.Song

class PlayingListDB(context: Context) {

    private val tag = "PlayingNowDBHelper"
    private val dbName = "PlayingList"
    private val dbVersion = 1

    private var name: String = "name"
    private var location: String = "location"
    private var dbHelper: DBHelper = DBHelper(context, dbName, null, dbVersion)
    private lateinit var db: SQLiteDatabase

    private fun connectToBD() {
        Log.d(tag, "ConnectToBD")
        db =  dbHelper.writableDatabase
    }
    private fun disconnectBD() {
        Log.d(tag, "DisconnectToBD")
        dbHelper.close()
    }

    fun addToDB(SongName: String, SongLocation: String) {
        connectToBD()
        Log.d(tag, "addToBd")

        val cv = ContentValues()
        cv.put(name, SongName)
        cv.put(location, SongLocation)
        //val rowID: Long =
        db.insert(dbName, null, cv)

        disconnectBD()
    }

    fun getFromDB(): List<Song> {
        connectToBD()
        Log.d(tag, "getFromBD")

        val c: Cursor = db.query(dbName, null, null, null, null, null, null)
        val songList = mutableListOf<Song>()

        if(c.moveToFirst()) {
            val nameColIndex = c.getColumnIndex(name)
            val emailColIndex = c.getColumnIndex(location)

            do {
                songList.add(Song(c.getString(nameColIndex), c.getString(emailColIndex)))
            } while (c.moveToNext())
        }
        else {
            Log.d(tag, "0 rows")
        }
        c.close()


        disconnectBD()
        return songList
    }

    fun deleteBd() {
        connectToBD()
        Log.d(tag, "delFromBD")

        //var clearCount =
        db.delete(dbName, null, null)

        disconnectBD()
    }

}