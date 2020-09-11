package com.muphbiu.musicplayer.services

import android.content.*
import android.util.Log

class PlayingReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.action) {
            "Previous" -> {
                Log.d("TAG", "Previous")
                context?.startService(Intent(context, PlayingService::class.java).putExtra("ACTION", 1))
            }
            "PlayPause" -> {
                Log.d("TAG", "PlayPause")
                context?.startService(Intent(context, PlayingService::class.java).putExtra("ACTION", 2))
            }
            "Next" -> {
                Log.d("TAG", "Pp")
                context?.startService(Intent(context, PlayingService::class.java).putExtra("ACTION", 3))
            }
            "Stop" -> {
                Log.d("TAG", "Pp")
                context?.startService(Intent(context, PlayingService::class.java).putExtra("ACTION", 4))
            }
            else -> {
                Log.d("TAG", "Else")
            }
        }
    }

}