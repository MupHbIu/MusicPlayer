package com.muphbiu.musicplayer.ui

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.muphbiu.musicplayer.R
import kotlinx.android.synthetic.main.activity_menu.*

class SettingsActivity : AppCompatActivity() {
    companion object {
        const val APP_PREFERENCES = "APP_PREFERENCES"
        const val DARK_THEME = "DARK_THEME_MOD"
    }
    private var themeId = 0
    private lateinit var sPref: SharedPreferences

    private var darkTheme = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        sPref = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
        getSavedSettings()
        setSavedView()

        settingsBack.setOnClickListener { super.onBackPressed() }
        settingsSave.setOnClickListener {
            /*val editor = sPref.edit()
            editor.putBoolean(DARK_THEME, darkTheme)
            editor.apply()*/
        }

        settingsDarkThemeSwitch.setOnCheckedChangeListener { compoundButton, b ->
            darkTheme = !darkTheme
            val editor = sPref.edit()
            editor.putBoolean(DARK_THEME, darkTheme)
            editor.commit()
            if(compoundButton.isChecked)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun getSavedSettings() {
        darkTheme = sPref.getBoolean(DARK_THEME, false)
    }

    private fun setSavedView() {
        settingsDarkThemeSwitch.isChecked = darkTheme
    }
}