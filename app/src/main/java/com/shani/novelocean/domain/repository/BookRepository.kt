package com.shani.novelocean.domain.repository

import com.shani.novelocean.domain.model.Book
import com.shani.novelocean.domain.model.BookDetail
import com.shani.novelocean.domain.model.Chapter
import org.intellij.lang.annotations.Language
import java.io.File

interface BookRepository {
    suspend fun getAllBooks(): Result<List<Book>>
    suspend fun getBookById(id: String): Result<BookDetail>
    suspend fun getChapter(bookId: String, chapterNumber: Int):Result<Chapter>
    suspend fun uploadBook(file: File, title: String, author:String, language: String, category:String): Result<Book>
}