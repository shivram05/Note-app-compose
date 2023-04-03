package com.compose.data.repository

import com.compose.data.NoteDAO
import com.compose.model.NoteEntity
import kotlinx.coroutines.flow.Flow

class NoteRepository (private val noteDAO: NoteDAO) {

    fun getAllFlowData() : Flow<List<NoteEntity>> = noteDAO.getAll()

    suspend fun insertData(noteEntity: NoteEntity) = noteDAO.insert(noteEntity)
   suspend fun upateData(noteEntity: NoteEntity) = noteDAO.update(noteEntity)
   suspend fun deleteData(noteEntity: NoteEntity) = noteDAO.delete(noteEntity)


}