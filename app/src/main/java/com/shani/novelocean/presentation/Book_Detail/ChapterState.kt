package com.shani.novelocean.presentation.Book_Detail

import com.shani.novelocean.domain.model.Chapter

sealed class ChapterState {
     object Loading: ChapterState()
     data class Success(val chapter: Chapter): ChapterState()
    data class Error(val message: String): ChapterState()
}