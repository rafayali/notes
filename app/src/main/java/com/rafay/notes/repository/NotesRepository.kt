package com.rafay.notes.repository

import com.rafay.notes.repository.models.Note
import kotlinx.coroutines.flow.Flow

/**
 * Interface for Notes repository.
 */
interface NotesRepository {
    /**
     * Returns list of [Note] for logged-in user.
     *
     * @return [List] of [Note]
     */
    suspend fun observe(callback: (List<Note>) -> Unit): List<Note>

    /**
     * Returns list of [Note] for logged-in user.
     *
     * @return [Flow] of [Note]
     */
    suspend fun observe(): Flow<List<Note>>

    /**
     * Returns [Note] of specified [id].
     *
     * @return [Note]
     */
    suspend fun get(id: String): Note

    /**
     * Creates a new [Note] in repository.
     */
    suspend fun create(
        title: String,
        description: String,
        done: Boolean,
        backgroundHexColor: String
    )

    /**
     * Updates existing [Note].
     */
    suspend fun update(note: Note)

    /**
     * Deletes [Note] of specified [id].
     */
    suspend fun delete(id: String)
}