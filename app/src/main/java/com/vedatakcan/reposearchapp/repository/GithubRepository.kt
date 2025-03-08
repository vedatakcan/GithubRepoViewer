package com.vedatakcan.reposearchapp.repository

import com.vedatakcan.reposearchapp.model.Repo
import com.vedatakcan.reposearchapp.network.GithubApiService
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val apiService: GithubApiService
) {
    suspend fun getRepos(username: String): List<Repo> {
        return apiService.getUserRepos(username)
    }
}
