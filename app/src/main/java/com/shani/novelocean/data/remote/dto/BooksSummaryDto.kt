package com.shani.novelocean.data.remote.dto

data class BooksSummaryDto(
    val id: String,
    val title: String,
    val author: String,
    val language: String,
    val category: String,
    val uploaddate: String,
    val coverImage: String,
    val totalPages: Long
)
