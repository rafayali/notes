package com.rafay.notes.create

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import com.rafay.notes.databinding.ActivityCreateEditNoteBinding
import kotlinx.android.synthetic.main.activity_create_edit_note.view.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AddEditNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateEditNoteBinding

    private val viewModel by viewModel<AddEditNoteViewModel> { parametersOf(intent.extras) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

        setupViewModelObservers()
    }

    private fun initView() {
        binding.clParent.setOnApplyWindowInsetsListener { _, insets ->
            val lpToolbar = (binding.toolbar.layoutParams as ViewGroup.MarginLayoutParams).apply {
                topMargin += insets.systemWindowInsetTop
                leftMargin += insets.systemWindowInsetLeft
                rightMargin += insets.systemWindowInsetRight
            }
            binding.toolbar.layoutParams = lpToolbar

            // Set listener to null so insets are not re-applied
            binding.clParent.setOnApplyWindowInsetsListener(null)

            insets.consumeSystemWindowInsets()
        }

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.llColorPanel.image_blue_button.setOnClickListener {
            viewModel.setColor("2196F3")
        }

        binding.llColorPanel.image_green_button.setOnClickListener {
            viewModel.setColor("4CAF50")
        }

        binding.llColorPanel.image_purple_button.setOnClickListener {
            viewModel.setColor("673AB7")
        }

        binding.editTitle.doOnTextChanged { text, _, _, _ ->
            viewModel.setTitle(text.toString())
        }

        binding.editDescription.doOnTextChanged { text, _, _, _ ->
            viewModel.setNotes(text.toString())
        }
    }

    private fun setupViewModelObservers() {
        viewModel.title.observe(this, Observer {
            if (binding.editTitle.text.toString() != it) {
                binding.editTitle.setText(it)
            }
        })

        viewModel.notes.observe(this, Observer {
            if (binding.editDescription.text.toString() != it){
                binding.editDescription.setText(it)
            }
        })

        viewModel.color.observe(this, Observer {
            //            binding.flBackground.background = Color.parseColor("#$it").toDrawable()
        })
    }

    override fun onBackPressed() {
        viewModel.save()
        super.onBackPressed()
    }

    companion object {
        const val KEY_LONG_ID = "id"
        const val KEY_STRING_TITLE = "title"
        const val KEY_STRING_DESCRIPTION = "description"
        const val KEY_STRING_BG_COLOR_HEX = "bgColor"
    }
}
