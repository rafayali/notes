package com.rafay.notes.repository

import com.rafay.notes.repository.models.Note

/**
 * Interface for Notes repository.
 */
interface NotesRepository {
    /**
     * Returns list of [Note] for logged-in user.
     *
     * @return [List] of [Note]
     */
    suspend fun getAll(callback: (List<Note>) -> Unit): List<Note>

    /**
     * Returns [Note] of specified [id].
     *
     * @return [Note]
     */
    suspend fun get(id: String): Note

    /**
     * Creates a new [Note] in repository if it doesn't exists or updates if it exists.
     */
    suspend fun createOrUpdate(note: Note)

    /**
     * Deletes [Note] of specified [id].
     */
    suspend fun delete(id: String)
}