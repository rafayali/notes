package com.rafay.notes.repository.models

import com.google.firebase.Timestamp
import java.util.UUID

/**
 * Model class for notes remote data.
 */
data class Note(
    val id: String? = null,
    val itemId: Long = UUID.randomUUID().mostSignificantBits,
    val title: String? = null,
    val description: String? = null,
    val done: Boolean = false,
    val backgroundColor: String? = null,
    val date: Long = System.currentTimeMillis(),
    val dateCreated: Timestamp = Timestamp.now(),
    val dateModified: Timestamp = Timestamp.now()
)
