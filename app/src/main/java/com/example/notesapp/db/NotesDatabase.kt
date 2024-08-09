package com.example.notesapp.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.notesapp.NotesData

@Database(entities = [NotesData::class], version = 2, exportSchema = true)
@TypeConverters(Converter::class)
abstract class NotesDatabase : RoomDatabase() {

    companion object{
        const val DbName = "NotesDB"
    }

    abstract fun getNoteDao() : NoteDbDao
}