package com.rafay.notes.create

import android.os.Bundle
import com.rafay.notes.db.NotesDatabase
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val createNoteModule = module {
    viewModel { (bundle: Bundle?) ->
        val id = bundle?.getLong(CreateNoteFragment.KEY_LONG_ID)
        val title = bundle?.getString(CreateNoteFragment.KEY_STRING_TITLE)
        val notes = bundle?.getString(CreateNoteFragment.KEY_STRING_DESCRIPTION)
        val color = bundle?.getString(CreateNoteFragment.KEY_STRING_BG_COLOR_HEX)

        CreateNoteViewModel(
            id = if(id == 0L) null else id,
            title = title,
            notes = notes,
            color = color,
            notesDao = get<NotesDatabase>().getNotesDao()
        )
    }
}
