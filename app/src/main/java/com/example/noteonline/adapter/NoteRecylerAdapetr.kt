package com.example.noteonline.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.noteonline.R
import com.example.noteonline.model.Note
import com.example.noteonline.ui.uifragment.HomeFragDirections
import com.google.firebase.database.DatabaseReference
import java.text.SimpleDateFormat
import java.util.*

class NoteRecylerAdapetr() : RecyclerView.Adapter<NoteRecylerAdapetr.ViewHolder>() {
    lateinit var context: Context
    lateinit var listOfNote: List<Note>
    lateinit var fragment: Fragment
    lateinit var mref: DatabaseReference

    constructor(
        context: Context,
        lisOfNote: List<Note>,
        fragment: Fragment,
        mref: DatabaseReference
    ) : this() {
        this.context = context
        this.listOfNote = lisOfNote
        this.fragment = fragment
        this.mref = mref

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_of_note, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfNote.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listOfNote.get(position))
    }


    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        var textTitle = item.findViewById<TextView>(R.id.new_title)
        var textTime = item.findViewById<TextView>(R.id.new_date)
        var textContent = item.findViewById<TextView>(R.id.new_content)
        fun bind(note: Note) {
            textTitle.text = note.title.toString()
            textTime.text = note.timestamp.toString()
            textContent.text = note.content.toString()
            itemView.setOnClickListener {
                NavHostFragment.findNavController(fragment).navigate(
                    HomeFragDirections.actionHomeFragToDescriptionNote(
                        note.title.toString(),
                        note.content.toString()
                    )
                )
            }
            itemView.setBackgroundColor(Color.WHITE)
            itemView.setOnLongClickListener {
                val alertAnotherBuilder = AlertDialog.Builder(context)
                val views = LayoutInflater.from(context).inflate(R.layout.update_dialogue, null)
                val alertDialog = alertAnotherBuilder.create()
                alertDialog.setView(views)
                alertDialog.show()

                val upTitle = views.findViewById<EditText>(R.id.update_titleOfNote)
                upTitle.setText(note.title)
                val upContent = views.findViewById<EditText>(R.id.update_content_of_note)
                upContent.setText(note.content)
                val btnUpdate = views.findViewById<Button>(R.id.update_Dialoge)
                btnUpdate.setOnClickListener {
                    val currentTitle =
                        views.findViewById<EditText>(R.id.update_titleOfNote).text.toString()
                    val currentContent =
                        views.findViewById<EditText>(R.id.update_content_of_note).text.toString()
                    val currentNote = Note(
                        note.id!!,
                        currentTitle, currentContent, getCurrentData()
                    )
                    mref?.child(note.id!!)?.setValue(currentNote)
                    alertDialog.dismiss()
                }
                val btnDelete = views.findViewById<Button>(R.id.Delete_Dialoge)
                btnDelete.setOnClickListener {

                    mref?.child(note.id!!)?.removeValue()
                    alertDialog.dismiss()
                }
                false
            }

        }

    }

    private fun getCurrentData(): String {
        val calendar = Calendar.getInstance()
        val mformat = SimpleDateFormat("EEEE hh:mm a")
        val strdate = mformat.format(calendar.time)
        return strdate
    }


}


