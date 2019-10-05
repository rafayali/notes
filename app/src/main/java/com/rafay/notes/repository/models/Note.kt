package com.rafay.notes.repository.models

import com.google.firebase.Timestamp
import com.rafay.notes.home.NoteUiModel

/**
 * Data class to save/restore [Note] object from Firebase.
 */
data class Note(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val done: Boolean = false,
    val backgroundColorHex: String? = null,
    val date: Long = System.currentTimeMillis(),
    val dateCreated: Timestamp = Timestamp.now(),
    val dateModified: Timestamp = Timestamp.now()
)

fun Note.toNoteUiModel(): NoteUiModel {
    return NoteUiModel(
        title = title!!,
        description = description,
        backgroundColorHex = backgroundColorHex,
        done = done
    )
}