package com.example.semprace.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.semprace.model.Note
import com.example.semprace.repository.NoteRepository

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {
    private val _notes = MutableLiveData<List<Note>>()
    val notes: LiveData<List<Note>> get() = _notes

    init {
        _notes.value = repository.getNotes()
    }

    fun addNote(note: Note) {
        repository.addNote(note)
        _notes.value = repository.getNotes()
    }

    fun deleteNote(note: Note) {
        repository.deleteNote(note)
        _notes.value = repository.getNotes()
    }

    fun updateNote(note: Note) {
        repository.updateNote(note)
        _notes.value = repository.getNotes()
    }

}

