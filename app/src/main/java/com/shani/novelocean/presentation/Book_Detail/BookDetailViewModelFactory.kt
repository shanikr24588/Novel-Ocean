package com.shani.novelocean.presentation.Book_Detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shani.novelocean.data.di.NetworkModule
import com.shani.novelocean.domain.UseCase.GetBookDetailsUseCase
import com.shani.novelocean.domain.UseCase.GetChapterUseCase

class BookDetailViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookDetailViewModel::class.java)) {
            val repository = NetworkModule.provideBookRepository(
                NetworkModule.provideBookApi(
                    NetworkModule.provideRetrofit()
                )
            )
            val getBookDetailUseCase = GetBookDetailsUseCase(repository)
            val getChapterUseCase = GetChapterUseCase(repository)
            return BookDetailViewModel(getBookDetailUseCase, getChapterUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}