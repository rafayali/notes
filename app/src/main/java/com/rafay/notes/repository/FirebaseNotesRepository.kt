package com.rafay.notes.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.rafay.notes.repository.models.Note
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

/**
 * Concrete implementation of [NotesRepository] using firestore.
 */
class FirebaseNotesRepository : NotesRepository {

    private val notesCollection = FirebaseFirestore.getInstance().collection(
        FirestoreCollections.notes
    )

    @ExperimentalCoroutinesApi
    override suspend fun observe() = callbackFlow<List<Note>> {
        val listener =
            notesCollection.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                querySnapshot?.let { snapshot ->
                    offer(snapshot.toObjects(Note::class.java))
                }

                firebaseFirestoreException?.let { exception ->
                    close(exception)
                }
            }

        awaitClose { listener.remove() }
    }

    override suspend fun createOrUpdate(
        id: String?,
        title: String,
        description: String,
        done: Boolean,
        backgroundHexColor: String
    ) {
        val docRef = if (id == null) {
            notesCollection.document()
        } else {
            notesCollection.document(id)
        }

        val note = Note(
            id = docRef.id,
            title = title,
            description = description,
            backgroundColor = backgroundHexColor
        )
        docRef.set(note)
    }

    override suspend fun get(id: String): Note {
        TODO("not implemented")
    }

    override suspend fun delete(id: String) {
        TODO("not implemented")
    }
}