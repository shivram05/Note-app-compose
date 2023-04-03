package com.compose.noteapp.data

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.compose.data.AppDatabase
import com.compose.data.NoteDAO
import com.compose.model.NoteEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


//@RunWith(AndroidJUnit4::class)
class NoteDAOAndroidTest {

    private lateinit var sut : NoteDAO
    private lateinit var mDb: AppDatabase


    @Before
    fun createDb() {
        mDb = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        sut = mDb.noteDao()
    }

    @After
    fun closeDb() {
        mDb.close()
    }

    @Test
    fun testInsertNoteAndReadInList()= runBlocking {

        val fakeText = "Some Text"
        val fakeNote = NoteEntity(text = fakeText)

//        insert
        sut.insert(fakeNote)

        val noteList = sut.getAll().first()

//        assert this will check the expected and output to use this we will use google truth library

        // assert
        assertThat(noteList.first().text).isEqualTo(fakeText)
    }

}