package com.rafay.notes.home

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rafay.notes.R
import com.rafay.notes.databinding.ItemNoteBinding
import com.rafay.notes.ktx.toDp
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
        private val onItemSelected: NoteOnClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NoteUiModel) {
            val context = binding.root.context

            binding.note = item
            binding.let {
                it.root.setOnClickListener { onItemSelected.invoke(item.id, binding.root) }
            }
            binding.imageViewOptions.setOnClickListener { }
            binding.clParent.background = RippleDrawable(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        context,
                        R.color.noteRipple
                    )
                ),
                GradientDrawable().apply {
                    setStroke(
                        1.toDp(binding.root.context),
                        ContextCompat.getColor(binding.root.context, R.color.noteShapeBorder)
                    )
                    cornerRadius = 16.toDp(context).toFloat()
                    setColor(Color.parseColor(item.backgroundColor))
                },
                null
            )

            binding.executePendingBindings()
        }
    }
}

typealias NoteOnClickListener = (id: Long, view: View) -> Unit