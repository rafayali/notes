package com.rafay.notes.work

import android.content.Context
import androidx.work.WorkerParameters
import org.koin.dsl.module

@Suppress("EXPERIMENTAL_API_USAGE")
val workerModule = module {
    factory { (appContext: Context, workerParameters: WorkerParameters) ->
        SyncNotesWorker(
            context = appContext,
            params = workerParameters,
            notesDao = get(),
            api = get()
        )
    }
}
