package com.rafay.notes.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.rafay.notes.R
import com.rafay.notes.common.recyclerview.NoteSpaceItemDecoration
import com.rafay.notes.common.view.NotesTopAppBar
import com.rafay.notes.theme.NotesTheme

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNoteClicked: (id: Long) -> Unit,
    onAddNoteClicked: () -> Unit,
) {
    val state by viewModel.notesState.collectAsState()

    HomeScreenContent(
        state = state, onNoteClicked = onNoteClicked, onAddNoteClicked = onAddNoteClicked
    )
}

@Composable
fun HomeScreenContent(
    state: List<NoteUiModel>,
    onNoteClicked: (id: Long) -> Unit,
    onAddNoteClicked: () -> Unit,
) {
    val notesAdapter = remember {
        NotesAdapter { id, _ ->
            onNoteClicked(id)
        }
    }

    Scaffold(
        topBar = { NotesTopAppBar(title = "Notes") },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.navigationBarsPadding(),
                onClick = { onAddNoteClicked() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add_24dp),
                    contentDescription = null
                )
            }
        },
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            factory = { context ->
                RecyclerView(context).apply {
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

@Preview
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
            onAddNoteClicked = {},
        )
    }
}