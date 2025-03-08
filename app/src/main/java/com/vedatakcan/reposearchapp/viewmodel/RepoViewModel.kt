package com.vedatakcan.reposearchapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vedatakcan.reposearchapp.model.Repo
import com.vedatakcan.reposearchapp.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class RepoViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel() {

    private val _repos = MutableStateFlow<List<Repo>>(emptyList())
    val repos: StateFlow<List<Repo>> = _repos

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _username = MutableStateFlow<String?>(null)
    val username: StateFlow<String?> = _username

    fun getRepos(username: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val result = repository.getRepos(username)
                if (result.isEmpty()) {
                    _errorMessage.value = "Bu kullanıcıya ait repo bulunamadı."
                } else {
                    _repos.value = result
                    _username.value = username // Kullanıcı adını alıp ViewModel'e set edelim
                    _errorMessage.value = null // Hata mesajını temizle
                }

            } catch (e: Exception) {
                if (e is HttpException && e.code() == 404) {
                    _errorMessage.value =
                        "Kullanıcı bulunamadı"
                } else {
                    _errorMessage.value = "Hata oluştu: ${e.message}"
                }

            } finally {
                _isLoading.value = false
            }
        }
    }

}
