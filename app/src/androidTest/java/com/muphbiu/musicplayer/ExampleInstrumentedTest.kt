package com.muphbiu.musicplayer

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.muphbiu.musicplayer.data.ReadSongsFromSD

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import java.io.File

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.muphbiu.musicplayer", appContext.packageName)

        /*val readSongsFromSD = ReadSongsFromSD()
        assertEquals(true, readSongsFromSD.isContainMusic(File("/storage/F30F-1BFE/Music")))
        assertEquals(false, readSongsFromSD.isContainMusic(File("/storage/F30F-1BFE/Alarms")))*/
    }
}