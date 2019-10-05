package com.rafay.notes.create

import android.graphics.Color
import android.os.Bundle
import android.transition.Transition
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.core.transition.addListener
import androidx.core.view.ViewCompat
import com.rafay.notes.R
import com.rafay.notes.databinding.ActivityCreateEditNoteBinding
import com.rafay.notes.util.dataBinding
import kotlinx.android.synthetic.main.activity_create_edit_note.view.*
import org.koin.android.ext.android.inject

class AddEditNoteActivity : AppCompatActivity() {

    private val binding by dataBinding<ActivityCreateEditNoteBinding>(
        R.layout.activity_create_edit_note
    )

    private val viewModel by inject<AddEditNoteViewModel>()

    private lateinit var transition: Transition.TransitionListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent()

        applyTransitions()

        bindViews()
    }

    private fun bindViews() {
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.llColorPanel.image_blue_button.setOnClickListener {
            Toast.makeText(this, "Blue", Toast.LENGTH_SHORT).show()
        }

        binding.llColorPanel.image_green_button.setOnClickListener {
            Toast.makeText(this, "Green", Toast.LENGTH_SHORT).show()
        }

        binding.llColorPanel.image_purple_button.setOnClickListener {
            Toast.makeText(this, "Purple", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setContent() {
        val defaultColor =
            "#${Integer.toHexString(ContextCompat.getColor(this, R.color.defaultNoteColor))}"
        val bgColor =
            intent.extras?.getString(KEY_STRING_BG_COLOR_HEX, defaultColor) ?: defaultColor
        val title = intent.extras?.getString(KEY_STRING_TITLE, "") ?: ""
        val description = intent.extras?.getString(KEY_STRING_DESCRIPTION, "") ?: ""

        binding.textViewTitle.setText(title)
        binding.textViewDescription.setText(description)
        binding.flBackground.background = Color.parseColor(bgColor).toDrawable()
    }

    private fun applyTransitions() {
        ViewCompat.setTransitionName(binding.textViewTitle, VIEW_NAME_TITLE)
        ViewCompat.setTransitionName(binding.textViewDescription, VIEW_NAME_DESCRIPTION)

        transition = window.sharedElementEnterTransition.addListener(
            onCancel = {
                it.removeListener(transition)
            }, onEnd = {
                it.removeListener(transition)
            }
        )
    }

    companion object {
        const val KEY_STRING_TITLE = "title"
        const val KEY_STRING_DESCRIPTION = "description"
        const val KEY_STRING_BG_COLOR_HEX = "bgColor"

        const val VIEW_NAME_TITLE = "title"
        const val VIEW_NAME_DESCRIPTION = "description"
    }
}
