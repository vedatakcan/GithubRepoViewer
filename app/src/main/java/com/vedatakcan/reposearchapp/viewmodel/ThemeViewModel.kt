package com.vedatakcan.reposearchapp.viewmodel

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vedatakcan.reposearchapp.repository.ThemeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val themeRepository: ThemeRepository
) : ViewModel() {

    // Tema durumunu tutan LiveData
    private val _isDarkModeEnabled = MutableLiveData(themeRepository.isDarkModeEnabled())
    val isDarkModeEnabled: LiveData<Boolean> get() = _isDarkModeEnabled

    // Tema durumunu değiştir
    fun toggleTheme(isChecked: Boolean) {
        themeRepository.setDarkModeEnabled(isChecked)
        _isDarkModeEnabled.value = isChecked
        if (isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}
