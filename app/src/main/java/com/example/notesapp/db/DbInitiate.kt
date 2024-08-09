package com.example.notesapp.db

import android.app.Application
import androidx.room.Room

class DbInitiate : Application() {

    companion object{
        lateinit var notesDatabase : NotesDatabase
    }
    override fun onCreate() {
        super.onCreate()

        notesDatabase  = Room.databaseBuilder(applicationContext,
            NotesDatabase::class.java,
            NotesDatabase.DbName)
            .fallbackToDestructiveMigration()
            .build()
    }
}