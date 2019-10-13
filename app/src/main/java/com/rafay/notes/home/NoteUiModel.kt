package com.rafay.notes.home

import com.rafay.notes.repository.models.Note
import java.util.*

/**
 * Ui model class for [Note].
 */
data class NoteUiModel(
    val uId: Long = UUID.randomUUID().mostSignificantBits,
    val title: String,
    val description: String?,
    val backgroundColorHex: String? = null,
    val done: Boolean
)