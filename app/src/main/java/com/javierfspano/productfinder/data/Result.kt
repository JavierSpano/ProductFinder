package com.javierfspano.productfinder.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

sealed class Result<T> {
    data class Success<T>(val value: T) : Result<T>()
    object NetworkError : Result<Nothing>()
    object GenericError : Result<Nothing>()
}

suspend fun <T> getResult(block: suspend () -> T) = withContext(Dispatchers.IO) {
    try {
        Result.Success(block.invoke())
    } catch (throwable: Throwable) {
        when (throwable) {
            is IOException,
            is HttpException -> Result.NetworkError
            else -> {
                Result.GenericError
            }
        }
    }
}

