package com.rafay.notes

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

@Suppress("unused")
class NotesApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this)

        startKoin {
            androidContext(applicationContext)
            modules(modules)
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}