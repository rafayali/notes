package com.rafay.notes

import androidx.room.Room
import com.rafay.notes.create.createNoteModule
import com.rafay.notes.db.NotesDatabase
import com.rafay.notes.home.homeModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.Koin
import org.koin.dsl.module

/**
 * Global application modules.
 */
val appModules = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            NotesDatabase::class.java,
            NotesDatabase.NAME
        ).fallbackToDestructiveMigration().build()
    }
}

/**
 * List of [Koin] modules.
 */
val modules = listOf(
    appModules,
    homeModule,
    createNoteModule
)
