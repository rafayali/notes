package com.rafay.notes

import com.rafay.notes.create.addEditNoteModule
import com.rafay.notes.home.homeModule
import com.rafay.notes.repository.FirebaseNotesRepository
import com.rafay.notes.repository.NotesRepository
import org.koin.core.Koin
import org.koin.dsl.module

/**
 * Global application modules.
 */
val appModules = module {
    module { single<NotesRepository> { FirebaseNotesRepository() } }
}

/**
 * List of [Koin] modules.
 */
val modules = listOf(
    appModules,
    homeModule,
    addEditNoteModule
)