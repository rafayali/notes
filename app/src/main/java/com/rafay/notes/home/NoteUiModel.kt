package com.rafay.notes.home

/**
 * Ui model class for [Note].
 */
data class NoteUiModel(
    val id: Long,
    val noteId: Long?,
    val title: String?,
    val description: String?,
    val colorTag: String? = null,
    val done: Boolean
) {
    fun isTitleBlank() = title?.isBlank() == true
}
