package com.rafay.notes.work

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.DefinitionParameters

@KoinApiExtension
class KoinWorkerFactory : WorkerFactory(), KoinComponent {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker {
        return get(parameters = { DefinitionParameters(listOf(appContext, workerParameters)) })
    }
}
