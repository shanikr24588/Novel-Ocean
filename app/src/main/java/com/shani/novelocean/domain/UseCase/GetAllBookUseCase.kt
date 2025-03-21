package com.shani.novelocean.domain.UseCase

import com.shani.novelocean.common.Resource
import com.shani.novelocean.domain.model.Book
import com.shani.novelocean.domain.repository.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetAllBookUseCase(private val repository: BookRepository) {
    suspend operator fun invoke(): Flow<Resource<List<Book>>> = flow {
        emit(Resource.Loading())
        try {
            val result = repository.getAllBooks()
            result.fold(
                onSuccess = { books->
                    emit(Resource.Success(data = books))
                },
                onFailure = { exception ->
                    emit(Resource.Error(message = exception.message ?: "An unknown error occurred"))

                }
            )
        } catch (e: Exception){
            emit(Resource.Error(message = e.message ?: "An unknown error occured"))
        }
    }.flowOn(Dispatchers.IO)
}