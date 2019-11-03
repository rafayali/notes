package com.rafay.notes.create

import android.os.Bundle
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val addEditNoteModule = module {
    viewModel { (bundle: Bundle?) ->
        val id = bundle?.getString(AddEditNoteActivity.KEY_STRING_ID)
        val title = bundle?.getString(AddEditNoteActivity.KEY_STRING_TITLE)
        val notes = bundle?.getString(AddEditNoteActivity.KEY_STRING_DESCRIPTION)
        val color = bundle?.getString(AddEditNoteActivity.KEY_STRING_BG_COLOR_HEX)

        AddEditNoteViewModel(
            id = id,
            title = title,
            notes = notes,
            color = color ?: AddEditNoteViewModel.DEFAULT_COLOR,
            notesRepository = get()
        )
    }
}