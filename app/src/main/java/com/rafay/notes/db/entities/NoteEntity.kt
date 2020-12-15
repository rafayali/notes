@file:Suppress("NOTHING_TO_INLINE")

package com.rafay.notes.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rafay.notes.home.NoteUiModel
import java.time.OffsetDateTime

@Entity(tableName = "notes")
class NoteEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long?,

    @ColumnInfo(name = "noteId")
    val noteId: Long? = null,

    @ColumnInfo(name = "title")
    val title: String? = null,

    @ColumnInfo(name = "notes")
    val notes: String?,

    @ColumnInfo(name = "archived")
    val archived: Boolean = false,

    @ColumnInfo(name = "color_tag")
    val colorTag: String?,

    @ColumnInfo(name = "createdAt")
    val dateCreated: OffsetDateTime = OffsetDateTime.now(),

    @ColumnInfo(name = "modifiedAt")
    val dateModified: OffsetDateTime = OffsetDateTime.now()
)

inline fun NoteEntity.toNoteUiModel(): NoteUiModel {
    return NoteUiModel(
        id = requireNotNull(id),
        noteId = noteId,
        title = title,
        description = notes,
        colorTag = colorTag,
        done = false
    )
}
