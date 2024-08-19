package com.example.semprace.repository


import android.content.Context
import com.example.semprace.model.Note
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson

import java.io.File

class NoteRepository(private val context: Context) {
    private val notesFile = File(context.filesDir, "notes.json")
    private val gson = Gson()
    private var notes: MutableList<Note> = mutableListOf()

    init {
        if (notesFile.exists()) {
            val notesJson = notesFile.readText()
            val type = object : TypeToken<List<Note>>() {}.type
            notes = gson.fromJson(notesJson, type) ?: mutableListOf()
        }
    }

    fun getNotes(): List<Note> {
        return notes
    }

    fun addNote(note: Note) {
        notes.add(note)
        saveNotes()
    }

    fun deleteNote(note: Note) {
        notes.remove(note)
        saveNotes()
    }

    private fun saveNotes() {
        val notesJson = gson.toJson(notes)
        notesFile.writeText(notesJson)
    }

    fun updateNote(note: Note) {
        val index = notes.indexOfFirst { it.id == note.id }
        if (index != -1) {
            notes[index] = note
        }
    }
}