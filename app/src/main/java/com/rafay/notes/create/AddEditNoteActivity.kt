package com.rafay.notes.create

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.rafay.notes.databinding.ActivityCreateEditNoteBinding
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
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.editTitle.doOnTextChanged { text, _, _, _ ->
            viewModel.setTitle(text.toString())
        }

        binding.editDescription.doOnTextChanged { text, _, _, _ ->
            viewModel.setNotes(text.toString())
        }

        binding.buttonSelectColor.setOnClickListener {
            ColorsDialog { color ->
                viewModel.setColor(color)
            }.show(supportFragmentManager, ColorsDialog::class.java.simpleName)
        }
    }

    private fun setupViewModelObservers() {
        viewModel.title.observe(this, {
            if (binding.editTitle.text.toString() != it) {
                binding.editTitle.setText(it)
            }
        })

        viewModel.notes.observe(this, {
            if (binding.editDescription.text.toString() != it) {
                binding.editDescription.setText(it)
            }
        })

        viewModel.color.observe(this, {
            // todo set color indiator on UI
        })
    }

    override fun onBackPressed() {
        viewModel.sync()
        super.onBackPressed()
    }

    companion object {
        const val KEY_LONG_ID = "id"
        const val KEY_STRING_TITLE = "title"
        const val KEY_STRING_DESCRIPTION = "description"
        const val KEY_STRING_BG_COLOR_HEX = "bgColor"
    }
}
