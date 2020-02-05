package com.rafay.notes.repository

import com.rafay.notes.repository.models.Note
import kotlinx.coroutines.flow.Flow

/**
 * Interface for Notes repository.
 */
interface NotesRemoteRepository {

    /**
     * Returns list of [Note] for logged-in user.
     *
     * @return [Flow] of [Note]
     */
    suspend fun all(): Flow<List<Note>>

    /**
     * Returns [Note] of specified [id].
     *
     * @return [Note]
     */
    suspend fun get(id: String): Note

    /**
     * Creates a new [Note] in repository or updates existing [Note] of specified [id].
     */
    suspend fun createOrUpdate(
        id: String?,
        title: String,
        description: String,
        done: Boolean,
        backgroundHexColor: String
    )

    /**
     * Deletes [Note] of specified [id].
     */
    suspend fun delete(id: String)
}
