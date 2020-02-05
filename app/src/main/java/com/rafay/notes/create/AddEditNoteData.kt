package com.rafay.notes.create

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddEditNoteData(
    val id: String,
    val title: String,
    val description: String?
) : Parcelable
