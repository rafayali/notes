package com.rafay.notes.home

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Pair
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.rafay.notes.R
import com.rafay.notes.create.AddEditNoteActivity
import com.rafay.notes.databinding.ActivityHomeBinding
import com.rafay.notes.util.GridSpacingItemDecoration
import com.rafay.notes.util.Result
import com.rafay.notes.util.dataBinding
import org.koin.android.ext.android.inject

class HomeActivity : AppCompatActivity() {

    private val binding by dataBinding<ActivityHomeBinding>(R.layout.activity_home)

    private val viewModel by inject<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.fab.setOnClickListener {
            Intent(this, AddEditNoteActivity::class.java).also {
                startActivity(it)
            }
        }

        setupRecyclerView()

        viewModel.notes.observe(this, Observer {
            when (it) {
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    (binding.recyclerView.adapter as NotesAdapter).submitList(it.data)
                }
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
    }

    private fun setupRecyclerView() {
        val notesAdapter = NotesAdapter { id, view ->
            val note = (viewModel.notes.value as Result.Success).data.first { it.id == id }

            val options = ActivityOptions.makeSceneTransitionAnimation(
                this,
                Pair(view.findViewById(R.id.text_title), AddEditNoteActivity.VIEW_NAME_TITLE),
                Pair(
                    view.findViewById(R.id.text_description),
                    AddEditNoteActivity.VIEW_NAME_DESCRIPTION
                )
            )

            val bundle = bundleOf(
                AddEditNoteActivity.KEY_STRING_TITLE to note.title,
                AddEditNoteActivity.KEY_STRING_DESCRIPTION to note.description,
                AddEditNoteActivity.KEY_STRING_BG_COLOR to note.backgroundColor
            )
            startActivity(
                Intent(
                    this,
                    AddEditNoteActivity::class.java
                ).apply { putExtras(bundle) },
                options.toBundle()
            )
        }

        binding.recyclerView.apply {
            layoutManager =
                GridLayoutManager(
                    this@HomeActivity,
                    2
                )
            addItemDecoration(
                GridSpacingItemDecoration(2, 24, true)
            )
            adapter = notesAdapter
        }

        /*
        Fixes an issue with AppBarLayout "liftOnScroll" function where AapBarLayout would not
        lift down when scrolling is idle after reaching top.
         */
        binding.nestedScrollView.setOnScrollChangeListener(
            NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->
                if (scrollY <= 0) {
                    binding.appBarLayout.setLifted(false)
                }
            }
        )
    }
}
