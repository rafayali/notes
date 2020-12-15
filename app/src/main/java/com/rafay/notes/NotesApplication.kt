package com.rafay.notes

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.rafay.notes.work.KoinWorkerFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinApiExtension
import org.koin.core.context.startKoin
import timber.log.Timber

@KoinApiExtension
@Suppress("unused")
class NotesApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        WorkManager.initialize(
            applicationContext,
            Configuration.Builder().setWorkerFactory(KoinWorkerFactory()).build()
        )

        startKoin {
            androidContext(applicationContext)
            modules(modules)
        }
    }
}
