package com.shani.novelocean.presentation.Book_Detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shani.novelocean.common.Resource
import com.shani.novelocean.domain.UseCase.GetBookDetailsUseCase
import com.shani.novelocean.domain.UseCase.GetChapterUseCase
import com.shani.novelocean.domain.model.BookDetail
import com.shani.novelocean.domain.model.Chapter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookDetailViewModel(
    private val getBookDetailsUseCase: GetBookDetailsUseCase,
    private val getChapterUseCase: GetChapterUseCase
) : ViewModel(){

    private val _bookState = MutableStateFlow<BookDetailState>(BookDetailState.Loading)
    val bookState: StateFlow<BookDetailState> = _bookState

    private val _currentChapter = MutableStateFlow<ChapterState>(ChapterState.Loading)
    val currentChapter: StateFlow<ChapterState> = _currentChapter

    fun loadBookDetail(bookId: String){
        viewModelScope.launch {
            _bookState.value = BookDetailState.Loading
            val result = getBookDetailsUseCase(bookId)
            _bookState.value = when(result){
                is Resource.Loading<*> -> {
                    BookDetailState.Loading
                }
                is Resource.Success<*> -> {
                    val book = result.data as? BookDetail
                    if (book != null){
                        BookDetailState.Success(book)
                    } else{
                        BookDetailState.Error("Book not found")
                    }

                }
                is Resource.Error<*> -> {
                    val error = result.message ?: "Unknown Error"
                    BookDetailState.Error(error)
                } else-> BookDetailState.Error("Unknown Error")
            }
        }
    }

    fun loadChapter(bookId: String, chapterNumber: Int){
        viewModelScope.launch {
            _currentChapter.value = ChapterState.Loading
            val result = getChapterUseCase(bookId, chapterNumber)

            _currentChapter.value = when(result){
                is Resource.Loading<*> -> {
                    ChapterState.Loading
                }

                is Resource.Success<*> -> {
                    val chapter = result.data as? Chapter
                    if (chapter != null){
                        ChapterState.Success(chapter)
                    } else{
                        ChapterState.Error("Chapter not found")
                    }

                }
                is Resource.Error<*> ->{
                    val error = result.message ?: "Unknown Error"
                    ChapterState.Error(error)
                } else -> ChapterState.Error("Unknown Error")
            }
        }
    }


}