package com.shani.novelocean.presentation.Book_Detail

import com.shani.novelocean.domain.model.BookDetail

sealed class BookDetailState {

     object Loading : BookDetailState()
     data class Success(val book: BookDetail): BookDetailState()
    data class Error(val message: String): BookDetailState()
}