package com.vedatakcan.reposearchapp.model

data class Repo(
    val name: String,
    val description: String?,
    val stars: Int,
    val language: String?,
    val username: String?
)
