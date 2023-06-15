package com.rafay.notes.home

/**
 * Ui model class for [Note].
 */
data class NoteUiModel(
    val id: Int,
    val noteId: Long?,
    val title: String,
    val description: String,
    val colorTag: String? = null,
    val done: Boolean
)
