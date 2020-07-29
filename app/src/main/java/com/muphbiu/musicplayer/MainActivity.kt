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

class MainActivity : AppCompatActivity() {
    private val tag = "MainActivity"
    private val requestCode = 100

    private lateinit var model: RawSongsFile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), requestCode)

        model = RawSongsFile(this)

        button2.setOnClickListener {
            startActivity(Intent(this, MusicPlayingActivity::class.java))
        }
        mainActB2.setOnClickListener {
            startActivity(Intent(this, TestActivity::class.java))
        }
        getBtn.setOnClickListener {
            model.addSongsToFile(this)
            Toast.makeText(this, "Data refreshed.", Toast.LENGTH_LONG).show()
        }
        delBtn.setOnClickListener {
            model.deleteFile(this)
            Toast.makeText(this, "Data deleted.", Toast.LENGTH_LONG).show()
        }
    }

    /*
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == this.requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                listExternalStorage()
            } else {
                Toast.makeText(this, "Until you grant the permission, I cannot list the files", Toast.LENGTH_SHORT) .show()
            }
        }
    }

    private fun listExternalStorage() {
        val state = Environment.getExternalStorageState()

        if (Environment.MEDIA_MOUNTED == state || Environment.MEDIA_MOUNTED_READ_ONLY == state) {
            listFiles(Environment.getExternalStorageDirectory())
            Toast.makeText(this, "Successfully listed all the files!", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Recursively list files from a given directory.
     */
    private fun listFiles(directory: File) {
        val files = directory.listFiles()
        if (files != null) {
            for (file in files) {
                if (file != null) {
                    if (file.isDirectory) {
                        listFiles(file)
                    } else {
                        mainTV.text = mainTV.text.toString() + (file.absolutePath).toString()
                    }
                }
            }
        }
    }
    */
}



