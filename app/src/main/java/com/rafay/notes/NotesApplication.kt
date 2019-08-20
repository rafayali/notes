package com.rafay.notes

import android.app.Application
import timber.log.Timber

@Suppress("unused")
class NotesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}