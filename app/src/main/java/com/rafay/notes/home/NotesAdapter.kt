package com.rafay.notes.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rafay.notes.databinding.ItemNoteBinding
import com.rafay.notes.repository.models.Note

typealias NoteOnClickListener = (id: Long, view: View) -> Unit

/**
 * RecyclerView adapter for [Note].
 */
class NotesAdapter(
    private val onItemSelected: NoteOnClickListener
) : ListAdapter<NoteUiModel, NotesAdapter.NoteViewHolder>(TodoDiffCallback) {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false),
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
     * ViewHolder for [NoteUiModel].
     */
    class NoteViewHolder(
        private val binding: ItemNoteBinding,
        private val onItemSelected: NoteOnClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NoteUiModel) {
            binding.textTitle.text = item.title
            binding.textDescription.text = item.description

            if (item.isTitleBlank()) {
                binding.textTitle.visibility = View.GONE
            } else {
                binding.textTitle.visibility = View.VISIBLE
            }

            if (item.colorTag != null) {
                binding.imageColorTag.setBackgroundColor(Color.parseColor("#${item.colorTag}"))
            } else {
                binding.imageColorTag.setBackgroundColor(Color.TRANSPARENT)
            }

            binding.root.setOnClickListener { onItemSelected.invoke(item.id, binding.root) }
        }
    }

    /**
     * Diff utility for [NotesAdapter].
     */
    private object TodoDiffCallback : DiffUtil.ItemCallback<NoteUiModel>() {
        override fun areItemsTheSame(oldItem: NoteUiModel, newItem: NoteUiModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteUiModel, newItem: NoteUiModel): Boolean {
            return oldItem == newItem
        }
    }
}
