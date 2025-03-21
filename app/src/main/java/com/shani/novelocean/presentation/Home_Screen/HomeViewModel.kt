package com.shani.novelocean.presentation.Home_Screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shani.novelocean.common.Resource
import com.shani.novelocean.domain.UseCase.GetAllBookUseCase
import com.shani.novelocean.domain.model.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val getAllBookUseCase: GetAllBookUseCase):ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        fetchBooks()
    }

    fun fetchBooks(){
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            val result = getAllBookUseCase()
            _uiState.value = when(result){
                is Resource.Loading<*> ->{
                    HomeUiState.Loading
                }
                is Resource.Success<*> -> {
                    val books = result.data as? List<Book> ?: emptyList()
                    if (books != null){
                        HomeUiState.Success(books)
                    } else{
                        HomeUiState.Error("Books not found")

                    }
                }
                is Resource.Error<*> -> {
                    val error = result.message ?: "Unknown error"
                    HomeUiState.Error(error)
                }
                else-> HomeUiState.Error("Unknown Error")
            }


        }
    }
}