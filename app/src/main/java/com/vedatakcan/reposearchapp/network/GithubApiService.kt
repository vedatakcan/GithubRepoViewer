package com.vedatakcan.reposearchapp.network

import com.vedatakcan.reposearchapp.model.Repo
import retrofit2.http.GET
import retrofit2.http.Path

// GitHub API'den belirli bir kullanıcının reposunu al
interface GithubApiService {
    @GET("users/{username}/repos")
    suspend fun getUserRepos(@Path("username") username: String): List<Repo>
}

