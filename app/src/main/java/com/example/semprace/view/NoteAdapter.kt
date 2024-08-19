package com.example.semprace.view


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.semprace.R
import com.example.semprace.model.Note
import java.text.SimpleDateFormat
import java.util.*

class NoteAdapter(
    private var notes: List<Note>,
    private val onDeleteClick: (Note) -> Unit,
    private val onEditClick: (Note) -> Unit
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note)

        holder.itemView.setOnClickListener {
            onEditClick(note)
        }

        holder.itemView.findViewById<View>(R.id.deleteButton).setOnClickListener {
            onDeleteClick(note)
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun updateNotes(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged()
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        private val timestampTextView: TextView = itemView.findViewById(R.id.timestampTextView)

        fun bind(note: Note) {
            titleTextView.text = note.title
            contentTextView.text = note.content

            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val date = Date(note.timestamp)
            timestampTextView.text = dateFormat.format(date)
        }
    }
}
