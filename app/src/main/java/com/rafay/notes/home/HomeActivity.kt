package com.rafay.notes.home

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.rafay.notes.R
import com.rafay.notes.common.Result
import com.rafay.notes.common.recyclerview.NoteSpaceItemDecoration
import com.rafay.notes.create.AddEditNoteActivity
import com.rafay.notes.databinding.ActivityHomeBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Entry point of Notes.
 */
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private val viewModel by viewModel<HomeViewModel>()

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

        setupRecyclerView()

        setupViewModelObservers()
    }

    private fun initView() {
        binding.fab.setOnClickListener {
            Intent(this, AddEditNoteActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    @ExperimentalCoroutinesApi
    private fun setupRecyclerView() {
        val notesAdapter = NotesAdapter { id, _ ->
            val note = (viewModel.notesLocal.value as Result.Success).data.first { it.id == id }

            val bundle = bundleOf(
                AddEditNoteActivity.KEY_LONG_ID to note.id,
                AddEditNoteActivity.KEY_STRING_TITLE to note.title,
                AddEditNoteActivity.KEY_STRING_DESCRIPTION to note.description,
                AddEditNoteActivity.KEY_STRING_BG_COLOR_HEX to note.backgroundColorHex
            )

            startActivity(
                Intent(
                    this,
                    AddEditNoteActivity::class.java
                ).apply { putExtras(bundle) }
            )
        }

        binding.recyclerView.apply {
            layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setPadding(8, 8, 8, 8)
            clipToPadding = false
            clipChildren = false
            addItemDecoration(NoteSpaceItemDecoration())
            adapter = notesAdapter
        }
    }

    @ExperimentalCoroutinesApi
    private fun setupViewModelObservers() {
        viewModel.notesLocal.observe(this, Observer {
            when (it) {
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE

                    if (it.data.isEmpty()) {
                        binding.recyclerView.visibility = View.GONE

                        binding.clRetry.visibility = View.VISIBLE
                        binding.textMessage.visibility = View.VISIBLE
                        binding.textMessage.text = getString(R.string.home_no_notes)
                        binding.buttonRetry.visibility = View.GONE
                    } else {
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.clRetry.visibility = View.GONE
                    }

                    (binding.recyclerView.adapter as NotesAdapter).submitList(it.data)
                }
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.clRetry.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.clRetry.visibility = View.VISIBLE
                    binding.textMessage.text = getString(R.string.home_retry_text)
                    binding.buttonRetry.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                }
            }
        })
    }
}
