package com.rafay.notes

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rafay.notes.create.AddEditNoteActivity
import com.rafay.notes.databinding.ActivityHomeBinding
import com.rafay.notes.home.HomeViewModel
import com.rafay.notes.home.NotesAdapter
import com.rafay.notes.home.TodoViewModelFactory
import com.rafay.notes.util.VerticalSpaceItemDecoration

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private val viewModel: HomeViewModel by lazy {
        ViewModelProviders.of(this, TodoViewModelFactory()).get(HomeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        binding.fab.setOnClickListener {
            Intent(this, AddEditNoteActivity::class.java).also {
                startActivity(it)
            }
        }

        setupRecyclerView()

        viewModel.notes.observe(this, Observer {
            (binding.recyclerView.adapter as NotesAdapter).submitList(it)
        })
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(
                applicationContext,
                RecyclerView.VERTICAL,
                false
            )
            addItemDecoration(
                VerticalSpaceItemDecoration(
                    bottomInPx = 16f,
                    leftInPx = 8f,
                    topInPx = 8f,
                    rightInPx = 8f
                )
            )
        }

        val adapter = NotesAdapter { id ->
            val todo = viewModel.notes.value?.firstOrNull { it.id == id }
            todo?.let {
                val bundle = bundleOf(
                    AddEditNoteActivity.KEY_STRING_TITLE to todo.title,
                    AddEditNoteActivity.KEY_DESCRIPTION to todo.description
                )
                startActivity(
                    Intent(
                        this,
                        AddEditNoteActivity::class.java
                    ).apply { putExtras(bundle) })
            }
        }
        binding.recyclerView.adapter = adapter

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
