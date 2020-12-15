package com.rafay.notes.util

class Event<T>(private val data: T?) {

    private var handled = false

    /**
     * Returns the content and prevents its use again.
     */
    fun consume(): T? {
        return if (handled) {
            null
        } else {
            handled = true
            data
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peek(): T? = data
}