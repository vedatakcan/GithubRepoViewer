package com.vedatakcan.reposearchapp.repository

import android.content.SharedPreferences
import javax.inject.Inject

class ThemeRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    private val prefDarkMode = "PREF_DARK_MODE"

    // Tema durumu kontrolü
    fun isDarkModeEnabled(): Boolean {
        return sharedPreferences.getBoolean(prefDarkMode, false)
    }

    // Tema durumunu güncelleme
    fun setDarkModeEnabled(isEnabled: Boolean) {
        sharedPreferences.edit().putBoolean(prefDarkMode, isEnabled).apply()
    }
}

