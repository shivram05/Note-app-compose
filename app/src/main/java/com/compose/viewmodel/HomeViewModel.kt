package com.compose.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.compose.data.repository.NoteRepository
import com.compose.model.NoteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface HomeViewModelAbstract {

    val noteListFlow: Flow<List<NoteEntity>>

    val selectedNoteState : State<NoteEntity?>
    fun addOrUpdateNote(noteEntity: NoteEntity)
    fun updateNote(noteEntity: NoteEntity)
    fun deleteNote(noteEntity: NoteEntity)

    fun selectNote(noteEntity: NoteEntity)

    fun resetSelectedNote()

}

@HiltViewModel
class HomeViewModel
@Inject constructor(private val noteRepository: NoteRepository) : ViewModel(),
    HomeViewModelAbstract {

    private val ioScope = CoroutineScope(Dispatchers.IO)

    private val _selectedNoteState : MutableState<NoteEntity?> = mutableStateOf(null)

    override val selectedNoteState: State<NoteEntity?>
        get() = _selectedNoteState


    override val noteListFlow: Flow<List<NoteEntity>>
        get() = noteRepository.getAllFlowData()


    override fun addOrUpdateNote(noteEntity: NoteEntity) {
        ioScope.launch {
            if (noteEntity.id == null) {
                noteRepository.insertData(noteEntity)
            } else {
                noteRepository.upateData(noteEntity)
            }
        }

    }

    override fun updateNote(noteEntity: NoteEntity) {
        ioScope.launch {

            noteRepository.upateData(noteEntity)
        }
    }

    override fun deleteNote(noteEntity: NoteEntity) {
        ioScope.launch {

            noteRepository.deleteData(noteEntity)
        }
    }

    override fun selectNote(noteEntity: NoteEntity) {
        _selectedNoteState.value = noteEntity
    }

    override fun resetSelectedNote() {
        _selectedNoteState.value = null
    }
}