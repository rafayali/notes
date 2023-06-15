package com.rafay.notes.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rafay.notes.databinding.ItemNoteBinding

typealias NoteOnClickListener = (id: Long, view: View) -> Unit

