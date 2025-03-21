package com.shani.novelocean.presentation.Home_Screen

import com.shani.novelocean.domain.model.Book

sealed class HomeUiState {

    object Loading: HomeUiState()
    data class Success(val books: List<Book>): HomeUiState()
    data class Error(val message: String): HomeUiState()
}