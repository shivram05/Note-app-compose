package com.compose.viewmodel

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

    fun addNote(noteEntity: NoteEntity)
    fun updateNote(noteEntity: NoteEntity)
    fun deleteNote(noteEntity: NoteEntity)
}

@HiltViewModel
class HomeViewModel
@Inject constructor(private val noteRepository: NoteRepository) : ViewModel(),
    HomeViewModelAbstract {

    private val ioScope = CoroutineScope(Dispatchers.IO)

    override val noteListFlow: Flow<List<NoteEntity>>
        get() = noteRepository.getAllFlowData()

    override fun addNote(noteEntity: NoteEntity) {
        ioScope.launch {
            noteRepository.insertData(noteEntity)
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

}