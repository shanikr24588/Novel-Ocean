package com.shani.novelocean.data.repository

import com.shani.novelocean.data.remote.BookApiService
import com.shani.novelocean.data.remote.dto.BookDto
import com.shani.novelocean.data.remote.dto.BooksSummaryDto
import com.shani.novelocean.data.remote.dto.ChapterDto
import com.shani.novelocean.domain.model.Book
import com.shani.novelocean.domain.model.BookDetail
import com.shani.novelocean.domain.model.Chapter
import com.shani.novelocean.domain.repository.BookRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class BookRepositoryImpl(private val apiService: BookApiService):BookRepository {
    override suspend fun getAllBooks(): Result<List<Book>> {
         return try {
             val response = apiService.getAllBook()
             if (response.isSuccessful){
                 val books = response.body()?.map { it.toBookSummary()} ?: emptyList()
                 Result.success(books)
             } else{
                 Result.failure(Exception("Failed to fetch books: ${response.code()}"))
             }
         } catch (e: Exception){
             Result.failure(e)
         }
    }

    override suspend fun getBookById(id: String): Result<BookDetail> {
        return try {
            val response = apiService.getBookDetails(id)
            if (response.isSuccessful){
                val bookDetail = response.body() ?.toBook()
                if (bookDetail != null){
                    Result.success(bookDetail)
                } else{
                    Result.failure(Exception("Book not found"))
                }

            } else{
                Result.failure(Exception("Failed to fetch book details: ${response.code()}"))
            }
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun getChapter(bookId: String, chapterNumber: Int): Result<Chapter> {
        return try {
            val response = apiService.getChapter(bookId, chapterNumber)
            if (response.isSuccessful){
                val chapter = response.body() ?.toChapter()
                if (chapter != null){
                    Result.success(chapter)
                }else{
                    Result.failure(Exception("Chapter not found"))
                }
            } else{
                Result.failure(Exception("Failed to fetch chapter: ${response.code()}"))
            }
        } catch (e:Exception){
            Result.failure(e)
        }
    }

    override suspend fun uploadBook(
        file: File,
        title: String,
        author: String,
        language: String,
        category: String
    ): Result<Book> {
        return try {
            val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val filePart = MultipartBody.Part.createFormData("file", file.name, requestFile)

            val titleBody = title.toRequestBody("text/plain".toMediaTypeOrNull())
            val authorBody = author.toRequestBody("text/plain".toMediaTypeOrNull())
            val languageBody = language.toRequestBody("text/plain".toMediaTypeOrNull())
            val categoryBody = category.toRequestBody("text/plain".toMediaTypeOrNull())

            val response = apiService.uploadBook(filePart, titleBody, authorBody, languageBody, categoryBody)
            if (response.isSuccessful) {
                val book = response.body()?.toBookSummary()
                if (book != null) {
                    Result.success(book)
                } else {
                    Result.failure(Exception("Failed to parse uploaded book"))
                }
            } else {
                Result.failure(Exception("Failed to upload book: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    private fun BooksSummaryDto.toBookSummary() = Book(
        id = id,
        title = title,
        author = author,
        language = language,
        category = category,
        UploadDate = uploaddate,
        coverImage = coverImage,
        totalPage = totalPages
    )

    private fun BookDto.toBook() = BookDetail(
        id = id,
        title = title,
        author = author,
        language = language,
        category = category,
        UploadDate = uploadDate,
        coverImage = coverImage,
        chapter = chapters.map {it.toChapter()},
        totalPage = totalPages
    )

    private fun ChapterDto.toChapter() = Chapter(
        id=id,
        title= title,
        content = content,
        chapterNumber = chapterNumber
    )
}

