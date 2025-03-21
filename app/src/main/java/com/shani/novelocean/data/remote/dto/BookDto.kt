package com.shani.novelocean.data.remote.dto

data class BookDto(
    val id: String,
    val title: String,
    val author: String,
    val language: String,
    val category: String,
    val uploadDate: String,
    val coverImage: String,
    val chapters: List<ChapterDto>,
    val totalPages: Long
)
