package com.compose.di

import android.app.Application
import com.compose.data.AppDatabase
import com.compose.data.NoteDAO
import com.compose.data.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideNodeRepository(
        noteDAO: NoteDAO
    ) : NoteRepository {
        return NoteRepository(noteDAO)
    }

    @Singleton
    @Provides
    fun provideAppDatabase(context: Application) : AppDatabase{
        return AppDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideNoteDao(appDatabase: AppDatabase) : NoteDAO{
        return appDatabase.noteDao()
    }
}