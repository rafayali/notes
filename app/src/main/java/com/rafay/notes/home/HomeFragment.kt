package com.rafay.notes.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.rafay.notes.R
import com.rafay.notes.common.Result
import com.rafay.notes.common.recyclerview.NoteSpaceItemDecoration
import com.rafay.notes.create.CreateNoteFragment
import com.rafay.notes.databinding.FragmentHomeBinding
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private lateinit var homeBinding: FragmentHomeBinding

    private val viewModel by viewModel<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)

        initView()

        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.notes.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    homeBinding.progressBar.visibility = View.GONE

                    if (it.data.isEmpty()) {
                        homeBinding.recyclerView.visibility = View.GONE

                        homeBinding.clRetry.visibility = View.VISIBLE
                        homeBinding.textMessage.visibility = View.VISIBLE
                        homeBinding.textMessage.text = getString(R.string.home_no_notes)
                        homeBinding.buttonRetry.visibility = View.GONE
                    } else {
                        homeBinding.recyclerView.visibility = View.VISIBLE
                        homeBinding.clRetry.visibility = View.GONE
                    }

                    (homeBinding.recyclerView.adapter as NotesAdapter).submitList(it.data)
                }
                is Result.Loading -> {
                    homeBinding.progressBar.visibility = View.VISIBLE
                    homeBinding.clRetry.visibility = View.GONE
                    homeBinding.recyclerView.visibility = View.GONE
                }
                is Result.Error -> {
                    homeBinding.progressBar.visibility = View.GONE
                    homeBinding.clRetry.visibility = View.VISIBLE
                    homeBinding.textMessage.text = getString(R.string.home_retry_text)
                    homeBinding.buttonRetry.visibility = View.VISIBLE
                    homeBinding.recyclerView.visibility = View.GONE
                }
            }
        }
    }

    private fun initView() {
        homeBinding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_createNoteFragment)
        }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val notesAdapter = NotesAdapter { id, _ ->
            val note = (viewModel.notes.value as Result.Success).data.first { it.id == id }

            findNavController().navigate(
                R.id.action_homeFragment_to_createNoteFragment,
                bundleOf(
                    CreateNoteFragment.KEY_LONG_ID to note.id,
                    CreateNoteFragment.KEY_STRING_TITLE to note.title,
                    CreateNoteFragment.KEY_STRING_DESCRIPTION to note.description,
                    CreateNoteFragment.KEY_STRING_BG_COLOR_HEX to note.colorTag
                )
            )
        }

        homeBinding.recyclerView.apply {
            layoutManager =
                StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL)
            setPadding(8, 8, 8, 8)
            clipToPadding = false
            clipChildren = false
            addItemDecoration(NoteSpaceItemDecoration())
            adapter = notesAdapter
        }
    }

    companion object {
        private const val SPAN_COUNT = 2
    }
}