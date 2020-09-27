package com.rafay.notes.create

import android.os.Bundle
import com.rafay.notes.db.NotesDatabase
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val addEditNoteModule = module {
    viewModel { (bundle: Bundle?) ->
        val id = bundle?.getLong(AddEditNoteActivity.KEY_LONG_ID)
        val title = bundle?.getString(AddEditNoteActivity.KEY_STRING_TITLE)
        val notes = bundle?.getString(AddEditNoteActivity.KEY_STRING_DESCRIPTION)
        val color = bundle?.getString(AddEditNoteActivity.KEY_STRING_BG_COLOR_HEX)

        AddEditNoteViewModel(
            id = id,
            title = title,
            notes = notes,
            color = color,
            notesDao = get<NotesDatabase>().getNotesDao()
        )
    }
}
