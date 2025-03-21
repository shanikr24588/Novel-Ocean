package com.shani.novelocean.data.remote

import com.shani.novelocean.data.remote.dto.BookDto
import com.shani.novelocean.data.remote.dto.BooksSummaryDto
import com.shani.novelocean.data.remote.dto.ChapterDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface BookApiService {
    @GET("api/books")
    suspend fun getAllBook():Response<List<BooksSummaryDto>>

    @GET("api/books/{id}")
    suspend fun getBookSummary(@Path("id") id: String): Response<BooksSummaryDto>

    @GET("api/books/{id}/details")
    suspend fun getBookDetails(@Path("id") id:String):Response<BookDto>

    @GET("api/books/{bookId}/chapters/{chapterNumber}")
    suspend fun getChapter(
        @Path("bookId") bookId: String,
        @Path("chapterNumber") chapterNumber: Int
    ):Response<ChapterDto>

    @Multipart
    @POST("api/books")
    suspend fun uploadBook(
        @Part("file") file:MultipartBody.Part,
        @Part("title") title: RequestBody,
        @Part("author") author: RequestBody,
        @Part("language") language: RequestBody,
        @Part("category") category: RequestBody
    ): Response<BooksSummaryDto>
}