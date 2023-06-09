package com.compose.noteapp.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.compose.model.NoteEntity
import com.compose.noteapp.R
import com.compose.viewmodel.HomeViewModelAbstract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


private enum class PopupState {

    OPEN, CLOSE, EDIT
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModelAbstract,
    onClickNote : (NoteEntity)->Unit,
    onClickAddNote : ()->Unit
) {

    val itemListState = homeViewModel.noteListFlow.collectAsState(initial = listOf())

    val textState = remember { mutableStateOf("") }
    val noteIdState: MutableState<Long?> = rememberSaveable { mutableStateOf(null) }
    val popupState = rememberSaveable { mutableStateOf(PopupState.CLOSE) }


    Scaffold {
        LazyColumn(
            modifier = Modifier.padding(it)
        ) {
            items(itemListState.value.size) { index ->
                val note = itemListState.value[index]
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onLongPress = {
//                                    delete the note on long press
                                    println("data is lcikec")
                                    homeViewModel.deleteNote(note)
                                }
                            )
                        }
                        .clickable {
                            noteIdState.value = note.id
                            textState.value = note.text
//                            popupState.value = PopupState.EDIT
//                            navigate to other screen
                            println("clicked data")
                            homeViewModel.selectNote(note)
                            onClickNote(note)
                        }

                        .height(54.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 16.dp, end = 16.dp),
                        text = note.text,
                        maxLines = 1
                    )
                    Spacer(
                        modifier = Modifier
                            .height(1.dp)
                            .fillMaxWidth()
                            .background(color = Color.Gray)
                            .alpha(0.54f)
                            .align(Alignment.BottomCenter)
                    )
                }
            }
            item {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        modifier = Modifier.align(Alignment.Center),
                        onClick = {
//                            popupState.value = PopupState.OPEN
                            homeViewModel.resetSelectedNote()
                            onClickAddNote()
                        }) {
                        Text(text = stringResource(id = R.string.screen_home_button_add_note_text))
                    }
                }
            }
        }

        when (popupState.value) {
            PopupState.OPEN -> {

                NotePopup(
                    onClickDismiss = {
                        popupState.value = PopupState.CLOSE
                    },
                    onClickSave = {
                        homeViewModel.addOrUpdateNote(noteEntity = NoteEntity(text = it))
                        popupState.value = PopupState.CLOSE
                    }
                )
            }
            PopupState.CLOSE -> {}

            PopupState.EDIT -> {
                NotePopup(
                    text = textState.value,
                    onClickDismiss = {
                        popupState.value = PopupState.CLOSE
                    },
                    onClickSave = {
                        homeViewModel.updateNote(
                            noteEntity = NoteEntity(
                                id = noteIdState.value, text = it
                            )
                        )
                        popupState.value = PopupState.CLOSE
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    Surface(modifier = Modifier.fillMaxSize()) {

        HomeScreen(
            homeViewModel = object : HomeViewModelAbstract {
                override val noteListFlow: Flow<List<NoteEntity>>
                    get() = flowOf(
                        listOf(
                            NoteEntity(text = "Note 1"),
                            NoteEntity(text = "Note 2"),
                            NoteEntity(text = "Note 3"),
                            NoteEntity(text = "Note 4"),
                            NoteEntity(text = "Note 5"),
                            NoteEntity(text = "Note 6")
                        )
                    )
                override val selectedNoteState: State<NoteEntity?>
                    get() = mutableStateOf(null)

                override fun addOrUpdateNote(noteEntity: NoteEntity) {
                    TODO("Not yet implemented")
                }

                override fun updateNote(noteEntity: NoteEntity) {
                    TODO("Not yet implemented")
                }

                override fun deleteNote(noteEntity: NoteEntity) {
                    TODO("Not yet implemented")
                }

                override fun selectNote(noteEntity: NoteEntity) {
                    TODO("Not yet implemented")
                }

                override fun resetSelectedNote() {
                    TODO("Not yet implemented")
                }

            },
            onClickNote = {},
            onClickAddNote = {}

        )
    }
}