package com.rafay.notes.home

import com.rafay.notes.repository.models.Note
import java.util.*

/**
 * Ui model class for [Note].
 */
data class NoteUiModel(
    val id: Long,
    val noteId: Long?,
    val title: String?,
    val description: String?,
    val backgroundColorHex: String? = null,
    val done: Boolean
)