package com.muphbiu.musicplayer.data

import android.content.Context

class PlayerInstanceValues(context: Context) {
    companion object {
        const val KEY_PREFERENCES = "PREFERENCES_PLAYING_SERVICE"
        const val PLAYING_PLAYLIST = "PLAYLIST_PLAYING_NOW"
        const val SONG_POSITION = "SONG_ID"
        const val CURRENT_TIME = "CURRENT_TIME"
        const val RANDOM = "RANDOM"
        const val PLAYING = "PLAYING"
        const val REPEAT = "LOOPING_MODE"
        //private val playerInstanceValues = PlayerInstanceValues()
    }
    private val sPref = context.getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE)

    // ========== S A V E ==========
    fun savePlayingPlaylist(playlist: String) {
        val editor = sPref.edit()
        editor.putString(PLAYING_PLAYLIST, playlist)
        editor.apply()
    }
    fun saveSongPosition(position: Int) {
        val editor = sPref.edit()
        editor.putInt(SONG_POSITION, position)
        editor.apply()
    }
    fun saveCurrentTime(time: Int) {
        val editor = sPref.edit()
        editor.putInt(CURRENT_TIME, time)
        editor.apply()
    }
    fun saveRandom(random: Boolean) {
        val editor = sPref.edit()
        editor.putBoolean(RANDOM, random)
        editor.apply()
    }
    fun savePlaying(playing: Boolean) {
        val editor = sPref.edit()
        editor.putBoolean(PLAYING, playing)
        editor.apply()
    }
    fun saveRepeat(repeat: Int) {
        val editor = sPref.edit()
        editor.putInt(REPEAT, repeat)
        editor.apply()
    }
    // ========== S A V E ==========

    // ========== G E T ==========
    fun getPlayingPlaylist(): String {
        return sPref.getString(PLAYING_PLAYLIST, "").toString()
    }
    fun getSongPosition(): Int {
        return sPref.getInt(SONG_POSITION, 0)
    }
    fun getCurrentTime(): Int {
        return sPref.getInt(CURRENT_TIME, 0)
    }
    fun getRandom(): Boolean {
        return sPref.getBoolean(RANDOM, false)
    }
    fun getPlaying(): Boolean {
        return sPref.getBoolean(PLAYING, false)
    }
    fun getRepeat(): Int {
        return sPref.getInt(REPEAT, -1)
    }
    // ========== G E T ==========
}