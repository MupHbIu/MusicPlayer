package com.muphbiu.musicplayer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.muphbiu.musicplayer.ui.MusicPlayingActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.Manifest
import android.widget.Toast
import android.content.pm.PackageManager
import android.os.Build
import com.muphbiu.musicplayer.data.RawSongsFile
import com.muphbiu.musicplayer.localTest.TestActivity
import com.muphbiu.musicplayer.ui.FileExplorerActivity
import com.muphbiu.musicplayer.ui.PlaylistActivity
import com.muphbiu.musicplayer.ui.StartActivity

class MainActivity : AppCompatActivity() {
    private val tag = "MainActivity"
    private val REQUEST_CODE = 100

    private lateinit var model: RawSongsFile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE)

        model = RawSongsFile(this)

        button2.setOnClickListener {
            startActivity(Intent(this, MusicPlayingActivity::class.java))
        }
        mainActB2.setOnClickListener {
            startActivity(Intent(this, TestActivity::class.java))
        }
        btnExoPlayer.setOnClickListener {
            //startActivity(Intent(this, PlayerActivity::class.java)) // EXOPLAYER
            startActivity(Intent(this, StartActivity::class.java))
        }
        getBtn.setOnClickListener {
            model.addSongsToFile(this)
            Toast.makeText(this, "Data refreshed.", Toast.LENGTH_LONG).show()
        }
        delBtn.setOnClickListener {
            model.deleteFile(this)
            Toast.makeText(this, "Data deleted.", Toast.LENGTH_LONG).show()
        }
        fileExplorerBtn.setOnClickListener {
            startActivity(Intent(this, FileExplorerActivity::class.java))
        }
        btnPlaylists.setOnClickListener {
            val intent = Intent(this, PlaylistActivity::class.java)
            intent.putExtra("playlist", "")
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            REQUEST_CODE -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, "Until you grant the permission, I cannot list the files", Toast.LENGTH_SHORT) .show()
                    finish()
                }
            }
        }
    }

}



