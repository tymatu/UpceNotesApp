package com.example.semprace.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.semprace.R
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.semprace.model.Note
import com.example.semprace.repository.NoteRepository
import com.example.semprace.viewmodel.NoteViewModel
import com.example.semprace.viewmodel.NoteViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var noteAdapter: NoteAdapter
    private val noteViewModel: NoteViewModel by viewModels {
        NoteViewModelFactory(NoteRepository(this))
    }

    private val addEditNoteLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val note = result.data?.getSerializableExtra("NOTE") as? Note
            note?.let {
                if (noteViewModel.notes.value?.any { existingNote -> existingNote.id == it.id } == true) {
                    noteViewModel.updateNote(it)
                } else {
                    noteViewModel.addNote(it)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        noteAdapter = NoteAdapter(noteViewModel.notes.value ?: emptyList(), { note ->
            noteViewModel.deleteNote(note)
        }, { note ->
            val intent = Intent(this, AddEditNoteActivity::class.java).apply {
                putExtra("NOTE_ID", note.id)
                putExtra("NOTE_TITLE", note.title)
                putExtra("NOTE_CONTENT", note.content)
            }
            addEditNoteLauncher.launch(intent)
        })

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = noteAdapter
        }

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            val intent = Intent(this, AddEditNoteActivity::class.java)
            addEditNoteLauncher.launch(intent)
        }

        noteViewModel.notes.observe(this, { notes ->
            noteAdapter.updateNotes(notes)
        })
    }
}
