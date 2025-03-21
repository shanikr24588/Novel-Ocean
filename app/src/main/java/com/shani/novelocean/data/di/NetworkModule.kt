package com.shani.novelocean.data.di

import com.shani.novelocean.data.remote.BookApiService
import com.shani.novelocean.data.repository.BookRepositoryImpl
import com.shani.novelocean.domain.repository.BookRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton

    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("http://localhost:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun provideBookApi(retrofit: Retrofit):BookApiService{
        return retrofit.create(BookApiService::class.java)
    }

    fun provideBookRepository(apiService: BookApiService):BookRepository{
        return BookRepositoryImpl(apiService)
    }
}