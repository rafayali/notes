package com.rafay.notes.util

/**
 * A generic class that holds values with its loading status.
 */
sealed class Result<out T>{

    data class Success<out T>(val data: T, val message: String? = null) : Result<T>()

    object Loading: Result<Nothing>()

    data class Error(val message: String): Result<Nothing>()
}