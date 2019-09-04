package com.rafay.notes.create

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.rafay.notes.R
import com.rafay.notes.databinding.ActivityCreateEditNoteBinding
import org.koin.android.ext.android.inject
import timber.log.Timber

class AddEditNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateEditNoteBinding

    private val viewModel by inject<AddEditNoteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_edit_note)

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    companion object {
        const val KEY_STRING_TITLE = "title"
        const val KEY_DESCRIPTION = "description"
    }
}
