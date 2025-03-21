package com.shani.novelocean.presentation.Home_Screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shani.novelocean.data.di.NetworkModule
import com.shani.novelocean.domain.UseCase.GetAllBookUseCase

class HomeViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            val repository = NetworkModule.provideBookRepository(
                NetworkModule.provideBookApi(
                    NetworkModule.provideRetrofit()
                )
            )
            val getAllBookUseCase = GetAllBookUseCase(repository)
            return HomeViewModel(getAllBookUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
