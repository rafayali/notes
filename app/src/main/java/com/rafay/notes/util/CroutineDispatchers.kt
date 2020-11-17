package com.rafay.notes.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface CoroutineDispatchers{

    fun io(): CoroutineDispatcher

    fun main(): CoroutineDispatcher

    fun default(): CoroutineDispatcher
}

class DefaultCoroutineDispatchers(): CoroutineDispatchers{
    override fun io() = Dispatchers.IO

    override fun main() = Dispatchers.Main

    override fun default(): CoroutineDispatcher {
        TODO("Not yet implemented")
    }

}