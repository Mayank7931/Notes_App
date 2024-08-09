package com.example.notesapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.db.DbInitiate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Date
import java.time.Instant

class NotesViewModel : ViewModel() {

    private val notesDao = DbInitiate.notesDatabase.getNoteDao()

    val notesLiveData: LiveData<List<NotesData>> = notesDao.getNotesDetails()

    fun addNote(title:String, details:String){

        viewModelScope.launch (Dispatchers.IO){

            notesDao.addNote(NotesData(0,title,details,Date.from(Instant.now())))
        }

    }

    fun deleteNote(id: Int){

        viewModelScope.launch (Dispatchers.IO){
           notesDao.deleteNote(id)
        }

    }

    fun showNote(){

        viewModelScope.launch (Dispatchers.IO){
           notesDao.getNotesDetails()
        }

    }

    fun updateNote(title: String, details: String, id: Int){
        viewModelScope.launch (Dispatchers.IO){
            notesDao.updateNote(title,details, id )
        }
    }


}