package com.rafay.notes.home

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rafay.notes.R
import com.rafay.notes.databinding.ItemNoteBinding
import com.rafay.notes.ktx.toDp
import com.rafay.notes.repository.models.Note

/**
 * RecyclerView adapter for [Note].
 */
class NotesAdapter(
    private val onItemSelected: NoteOnClickListener
) : ListAdapter<NoteUiModel, NotesAdapter.NoteViewHolder>(TodoDiffCallback()) {

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

            binding.root.setOnClickListener { onItemSelected.invoke(item.id, binding.root) }

//            binding.clParent.background = createNoteDrawable(binding.root.context, null)
        }

        /**
         * Creates [RippleDrawable] background for note item.
         */
        @Suppress("SameParameterValue")
        private fun createNoteDrawable(context: Context, backgroundColor: String?): RippleDrawable {
            return RippleDrawable(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        context,
                        R.color.noteRipple
                    )
                ),
                GradientDrawable().apply {
                    setStroke(
                        1.toDp(binding.root.context),
                        ContextCompat.getColor(binding.root.context, R.color.gray_400)
                    )
                    cornerRadius = 16.toDp(context).toFloat()
                    if (backgroundColor != null) {
                        setColor(Color.parseColor("#$backgroundColor"))
                    } else {
                        setColor(ContextCompat.getColor(context, R.color.white))
                    }
                },
                null
            )
        }
    }

    /**
     * Diff utility for [NotesAdapter].
     */
    private class TodoDiffCallback : DiffUtil.ItemCallback<NoteUiModel>() {
        override fun areItemsTheSame(oldItem: NoteUiModel, newItem: NoteUiModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteUiModel, newItem: NoteUiModel): Boolean {
            return oldItem == newItem
        }
    }
}

typealias NoteOnClickListener = (id: Long, view: View) -> Unit
