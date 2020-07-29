package com.muphbiu.musicplayer.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class PlayingListDBHelper(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    private val tag = "PlayingListDBHelper"

    override fun onCreate(p0: SQLiteDatabase?) {
        Log.d(tag, "onCreate() (DB)")
        p0?.execSQL("create table PlayingList ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "location text" + ");")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}