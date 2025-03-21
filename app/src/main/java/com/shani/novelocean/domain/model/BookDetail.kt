package com.shani.novelocean.domain.model

data class BookDetail(
    val id: String,
    val title: String,
    val author: String,
    val language: String,
    val category: String,
    val UploadDate: String,
    val coverImage: String,
    val chapter: List<Chapter>,
    val totalPage: Long
)
