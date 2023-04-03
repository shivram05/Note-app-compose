package com.compose.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.compose.model.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDAO {

    @Query("SELECT * FROM note")
    fun getAll():Flow<List<NoteEntity>>

    @Insert
    suspend fun insert(noteEntity: NoteEntity)

    @Delete
    suspend fun delete(noteEntity: NoteEntity)

    @Update
    suspend fun update(noteEntity: NoteEntity)
}