package com.rafay.notes.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.rafay.notes.common.recyclerview.NoteSpaceItemDecoration
import com.rafay.notes.common.view.NotesTopAppBar
import com.rafay.notes.theme.NotesTheme

@Composable
fun HomeScreen(viewModel: HomeViewModel, onNoteClicked: (id: Long) -> Unit) {
    val state by viewModel.notesState.collectAsState()
    HomeScreenContent(state = state, onNoteClicked = onNoteClicked)
}

@Composable
fun HomeScreenContent(state: List<NoteUiModel>, onNoteClicked: (id: Long) -> Unit) {
    val notesAdapter = remember {
        NotesAdapter { id, _ ->
            onNoteClicked(id)
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            NotesTopAppBar(title = "Notes")
            AndroidView(
                modifier = Modifier.weight(1f),
                factory = {
                    RecyclerView(it).apply {
                        layoutManager =
                            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                        setPadding(8, 8, 8, 8)
                        clipToPadding = false
                        clipChildren = false
                        addItemDecoration(NoteSpaceItemDecoration())
                        adapter = notesAdapter
                    }
                },
                update = {
                    notesAdapter.submitList(state)
                },
            )
        }

    }
}

@ExperimentalMaterialApi
@Preview(showSystemUi = true, device = Devices.NEXUS_6)
@Composable
fun HomeScreenContent_Preview() {
    NotesTheme {
        HomeScreenContent(
            state = listOf(
                NoteUiModel(
                    id = 1,
                    noteId = 23,
                    title = "Sarim's Birthday",
                    description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                    colorTag = null,
                    done = false,
                ),
                NoteUiModel(
                    id = 1,
                    noteId = 23,
                    title = "Sarim's Birthday",
                    description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                    colorTag = null,
                    done = false,
                ),
                NoteUiModel(
                    id = 1,
                    noteId = 23,
                    title = "Sarim's Birthday",
                    description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                    colorTag = null,
                    done = false,
                ),
            ),
            onNoteClicked = {},
        )
    }
}