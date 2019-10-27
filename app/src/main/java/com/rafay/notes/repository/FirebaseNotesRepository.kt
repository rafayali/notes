package com.rafay.notes.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.rafay.notes.repository.models.Note
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import timber.log.Timber

/**
 * Concrete implementation of [NotesRepository] using firestore.
 */
class FirebaseNotesRepository : NotesRepository {

    private val notesCollection = FirebaseFirestore.getInstance().collection(
        FirestoreCollections.notes
    )

    override suspend fun observe(callback: (noteList: List<Note>) -> Unit) = coroutineScope {
        val completable = CompletableDeferred<Unit>()
        val mutex = Mutex()
        var job: Job? = null

        val listener =
            notesCollection.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                querySnapshot?.let { snapshot ->
                    job?.cancel()
                    job = launch {
                        mutex.withLock {
                            callback(snapshot.toObjects(Note::class.java))
                        }
                    }
                }

                firebaseFirestoreException?.let { exception ->
                    Timber.d(exception)
                }
            }

        try {
            completable.await()
        } finally {
            listener.remove()
        }

        return@coroutineScope listOf<Note>()
    }

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

    override suspend fun create(
        title: String,
        description: String,
        done: Boolean,
        backgroundHexColor: String
    ) {
        val docRef = notesCollection.document()
        val note = Note(
            id = docRef.id,
            title = title,
            description = description,
            backgroundColor = backgroundHexColor
        )
        docRef.set(note)
    }

    override suspend fun update(note: Note) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun get(id: String): Note {
        TODO("not implemented")
    }

    override suspend fun delete(id: String) {
        TODO("not implemented")
    }

    companion object {
        const val FIRESTORE_COLLECTION_PATH = "notes"
    }
}