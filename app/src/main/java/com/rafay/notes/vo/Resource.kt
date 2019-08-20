package com.rafay.notes.vo

/**
 * A generic class that holds values with its loading status.
 *
 * @author Rafay Ali
 */
data class Resource<T>(
    val status: Status,
    val message: String?,
    val data: T?
) {
    companion object {
        fun <T> success(data: T?, message: String? = null): Resource<T> {
            return Resource(status = Status.SUCCESS, message = message, data = data)
        }

        fun <T> error(data: T?, message: String): Resource<T> {
            return Resource(status = Status.ERROR, message = message, data = null)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(status = Status.LOADING, message = null, data = null)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}