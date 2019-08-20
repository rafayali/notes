package com.rafay.notes.create

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.rafay.notes.R
import com.rafay.notes.databinding.ActivityCreateEditNoteBinding

class AddEditNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateEditNoteBinding

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
