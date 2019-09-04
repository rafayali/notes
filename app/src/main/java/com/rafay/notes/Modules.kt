package com.rafay.notes

import com.rafay.notes.create.addEditModule
import com.rafay.notes.home.homeModule
import com.rafay.notes.repository.FirebaseNotesRepository
import com.rafay.notes.repository.NotesRepository
import org.koin.core.Koin
import org.koin.dsl.module

/**
 * List of [Koin] modules.
 */
val modules = listOf(
    module { single<NotesRepository> { FirebaseNotesRepository() } },
    homeModule,
    addEditModule
)