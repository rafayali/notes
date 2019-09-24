package com.rafay.notes.home

import androidx.recyclerview.widget.DiffUtil

/**
 * Diff utility for [NotesAdapter].
 *
 * @author Rafay Ali
 */
class TodoDiffCallback : DiffUtil.ItemCallback<NoteUiModel>() {
    override fun areItemsTheSame(oldItem: NoteUiModel, newItem: NoteUiModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NoteUiModel, newItem: NoteUiModel): Boolean {
        return oldItem == newItem
    }
}