package com.rafay.notes.create

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.rafay.notes.R
import com.rafay.notes.databinding.ActivityCreateEditNoteBinding
import kotlinx.android.synthetic.main.activity_create_edit_note.view.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AddEditNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateEditNoteBinding

    private val viewModel by viewModel<AddEditNoteViewModel> { parametersOf(intent.extras) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()

        setupViewModelObservers()

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
    }

    private fun initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_edit_note)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

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

        binding.fabDone.setOnClickListener {
            viewModel.save()
            onBackPressed()
        }
    }

    private fun setupViewModelObservers() {
        viewModel.color.observe(this, Observer {
            binding.flBackground.background = Color.parseColor("#$it").toDrawable()
        })

        viewModel.validationEvent.observe(this, Observer {
            it.consume()?.let { event ->
                when (event) {
                    AddEditNoteViewModel.ValidationEvent.EmptyTitle -> {
                        Toast.makeText(
                            this,
                            "Please enter a title",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    AddEditNoteViewModel.ValidationEvent.Valid -> {
                        viewModel.save()
                    }
                }
            }
        })
    }

    companion object {
        const val KEY_STRING_ID = "id"
        const val KEY_STRING_TITLE = "title"
        const val KEY_STRING_DESCRIPTION = "description"
        const val KEY_STRING_BG_COLOR_HEX = "bgColor"
    }
}
