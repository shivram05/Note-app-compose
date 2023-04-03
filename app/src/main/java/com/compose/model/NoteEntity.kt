package com.compose.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "note")
data class NoteEntity (

    @PrimaryKey
    var id : Long?=null,

    val text :String
)