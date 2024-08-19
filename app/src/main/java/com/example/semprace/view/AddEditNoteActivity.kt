package com.example.semprace.view



import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.semprace.R
import com.example.semprace.model.Note

class AddEditNoteActivity : AppCompatActivity() {

    private var noteId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)

        val titleEditText = findViewById<EditText>(R.id.titleEditText)
        val contentEditText = findViewById<EditText>(R.id.contentEditText)
        val saveButton = findViewById<Button>(R.id.saveButton)


        intent?.let {
            noteId = it.getIntExtra("NOTE_ID", 0)
            titleEditText.setText(it.getStringExtra("NOTE_TITLE"))
            contentEditText.setText(it.getStringExtra("NOTE_CONTENT"))
        }

        saveButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val content = contentEditText.text.toString()
            val timestamp = System.currentTimeMillis()

            val note = Note(
                id = if (noteId != 0) noteId else System.currentTimeMillis().toInt(),
                title = title,
                content = content,
                timestamp = timestamp
            )

            val intent = Intent().apply {
                putExtra("NOTE", note)
            }
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}

