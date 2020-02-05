package com.rafay.notes.work

import android.content.Context
import androidx.room.Room
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

/**
 * Worker for syncing notes stored in [Room] database with server.
 */
class SyncNotesWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        // placeholder result
        return Result.success()
    }
}
