package com.rafay.notes.work

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

/**
 * Interface for implementing non blocking work schedulers which execute independent of
 * calling thread.
 */
interface WorkScheduler<T> {

    fun schedule(data: T? = null)
}

class SyncNotesScheduler(private val context: Context) : WorkScheduler<Nothing> {

    override fun schedule(data: Nothing?) {
        WorkManager.getInstance(context).enqueue(
            OneTimeWorkRequestBuilder<SyncNotesWorker>().build()
        )
    }
}