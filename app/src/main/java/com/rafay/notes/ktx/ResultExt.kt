package com.rafay.notes.ktx

import java.net.HttpURLConnection
import retrofit2.HttpException

fun <T> Result<T>.toError(): ErrorMessage? {
    return when (val ex = exceptionOrNull()) {
        is HttpException -> {
            when (ex.code()) {
                HttpURLConnection.HTTP_BAD_REQUEST -> ErrorMessage.BadRequest
                else -> ErrorMessage.GenericError
            }
        }
        is Exception -> ErrorMessage.GenericError
        else -> null
    }
}

enum class ErrorMessage {
    BadRequest,
    GenericError
}
