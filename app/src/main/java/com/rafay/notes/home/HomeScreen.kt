package com.rafay.notes.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rafay.notes.R
import com.rafay.notes.common.view.NotesTopAppBar
import com.rafay.notes.theme.NotesTheme

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNoteClicked: (id: Int) -> Unit,
    onAddNoteClicked: () -> Unit,
) {
    val state by viewModel.state

    HomeScreenContent(
        state = state,
        onNoteClicked = onNoteClicked,
        onAddNoteClicked = {
            onAddNoteClicked()
        },
    )
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun HomeScreenContent(
    state: List<NoteUiModel>,
    onNoteClicked: (id: Int) -> Unit,
    onAddNoteClicked: () -> Unit,
) {
    val systemPadding = WindowInsets.safeContent.asPaddingValues()

    Scaffold(
        topBar = { NotesTopAppBar(title = "Notes") },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.systemBarsPadding(),
                onClick = { onAddNoteClicked() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add_24dp),
                    contentDescription = null
                )
            }
        },
    ) {
        LazyVerticalStaggeredGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            columns = StaggeredGridCells.Fixed(2),
            contentPadding = PaddingValues(
                start = 8.dp,
                end = 8.dp,
                top = 8.dp,
                bottom = systemPadding.calculateBottomPadding()
            ),
            verticalItemSpacing = 8.dp,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.size) { index ->
                Note(noteUiModel = state[index], onClick = onNoteClicked)
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun Note(modifier: Modifier = Modifier, noteUiModel: NoteUiModel, onClick: (Int) -> Unit) {
    Card(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = Color(0xfff6f6f6),
        elevation = 0.dp,
        onClick = { onClick(noteUiModel.id) },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "20 April 2023", style = MaterialTheme.typography.caption)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = noteUiModel.title,
                style = MaterialTheme.typography.h6,
            )
            if (noteUiModel.description.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = noteUiModel.description, style = MaterialTheme.typography.body2
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
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

@ExperimentalMaterialApi
@Composable
@Preview
fun NoteItem_Preview() {
    NotesTheme {
        Note(noteUiModel = NoteUiModel(
            id = 1,
            noteId = 1,
            title = "Awesome Note!",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam a condimentum mi. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Nullam diam lectus, vehicula in mattis convallis, tristique eu risus. Integer iaculis elit leo, in cursus sapien pellentesque ac. Sed sed lectus at ligula bibendum auctor. Fusce quis metus at ligula rhoncus hendrerit. Duis vehicula scelerisque purus dapibus molestie. Suspendisse tincidunt sed urna nec facilisis. Aenean varius placerat velit, nec commodo est ornare a. Nam euismod orci vitae augue volutpat, et venenatis urna fringilla",
            done = false,
        ), onClick = {})
    }
}