package com.example.notesapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.notesapp.NotesData


@Dao
interface NoteDbDao {

    @Insert
    fun addNote(notesData: NotesData)

    @Query("DELETE FROM notes_table WHERE ID = :id")
    fun deleteNote(id : Int)

    @Query("SELECT * FROM notes_table")
    fun getNotesDetails() : LiveData<List<NotesData>>

    @Query("UPDATE notes_table SET TITLE = :title , DETAILS = :details WHERE ID = :id")
    fun updateNote(title:String, details:String,id: Int)
}