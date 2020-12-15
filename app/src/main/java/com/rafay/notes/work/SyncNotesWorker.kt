package com.rafay.notes.work

import android.content.Context
import androidx.room.Room
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.rafay.notes.api.Api
import com.rafay.notes.db.dao.NotesDao
import timber.log.Timber

/**
 * Worker for syncing notes stored in [Room] database with server.
 */
class SyncNotesWorker(
    context: Context,
    params: WorkerParameters,
    private val notesDao: NotesDao,
    private val api: Api
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val res = notesDao.getNotes()

        Timber.d("NotesSyncWorker")

        return Result.success()
    }
}
