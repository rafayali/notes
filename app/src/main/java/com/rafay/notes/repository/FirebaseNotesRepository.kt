package com.rafay.notes.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.rafay.notes.repository.models.Note
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import timber.log.Timber

/**
 * Concrete implementation of [NotesRepository] using firestore.
 */
class FirebaseNotesRepository : NotesRepository {

    private val todoCollection = FirebaseFirestore.getInstance().collection(
        FIRESTORE_COLLECTION_PATH
    )

    override suspend fun getAll(callback: (noteList: List<Note>) -> Unit) = coroutineScope {
        val completable = CompletableDeferred<Unit>()
        val mutex = Mutex()
        var job: Job? = null

        val listener =
            todoCollection.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                querySnapshot?.let { snapshot ->
                    job?.cancel()
                    job = launch {
                        mutex.withLock {
                            callback(snapshot.toObjects(Note::class.java).also { list ->
                                list.forEach { todo ->
                                    Timber.d(todo.toString())
                                }
                            })
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

    override suspend fun get(id: String): Note {
        TODO("not implemented")
    }

    override suspend fun createOrUpdate(note: Note) {
        TODO("not implemented")
    }

    override suspend fun delete(id: String) {
        TODO("not implemented")
    }

    companion object {
        const val FIRESTORE_COLLECTION_PATH = "notes"
    }
}