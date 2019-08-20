package com.rafay.notes.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rafay.notes.R
import com.rafay.notes.databinding.ItemNoteBinding
import com.rafay.notes.repository.models.Note

/**
 * RecyclerView adapter for [Note].
 *
 * @author Rafay Ali
 */
class NotesAdapter(
    private val onItemSelected: NoteOnClickListener
) : ListAdapter<NoteUiModel, NotesAdapter.NoteViewHolder>(TodoDiffCallback()) {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_note,
                parent,
                false
            ),
            onItemSelected = onItemSelected
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    /**
     * ViewHolder implementation for [NoteUiModel].
     *
     * @author Rafay Ali
     */
    class NoteViewHolder(
        private val binding: ItemNoteBinding,
        private val onItemSelected: (id: Long) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NoteUiModel) {
            binding.note = item
            binding.executePendingBindings()
            binding.let {
                it.root.setOnClickListener { onItemSelected.invoke(item.id) }
            }
            binding.imageViewOptions.setOnClickListener { }
        }
    }
}

typealias NoteOnClickListener = (id: Long) -> Unit