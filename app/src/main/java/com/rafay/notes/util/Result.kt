package com.rafay.notes.util

sealed class Result {

    data class Success<out T : Any>(val data: T?) : Result()

    object Loading : Result()
}
