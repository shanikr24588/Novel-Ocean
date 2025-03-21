package com.shani.novelocean.domain.model

data class Book(
    val id: String,
    val title: String,
    val author: String,
    val language: String,
    val category: String,
    val UploadDate: String,
    val coverImage: String,
    val totalPage: Long
)
