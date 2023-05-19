package com.rafay.notes.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface CoroutineDispatchers {

    fun io(): CoroutineDispatcher

    fun main(): CoroutineDispatcher

    fun default(): CoroutineDispatcher
}

class DefaultCoroutineDispatchers : CoroutineDispatchers {

    override fun io() = Dispatchers.IO

    override fun main() = Dispatchers.Main

    override fun default() = Dispatchers.Default

    companion object {
        private var INSTANCE: DefaultCoroutineDispatchers? = null

        fun getInstance(): DefaultCoroutineDispatchers {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: DefaultCoroutineDispatchers().also {
                    INSTANCE = it
                }
            }
        }
    }
}
