package com.example.noteonline.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import com.example.noteonline.R
import com.example.noteonline.model.Note

class ListViewAdapter(context: Context, listOfNote: ArrayList<Note>) :
    ArrayAdapter<Note>(context, 0, listOfNote) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view =LayoutInflater.from(context).inflate(R.layout.custom_row_of_list,parent,false)
        val currentNote = getItem(position)!!
        view.findViewById<TextView>(R.id.titlerow).text=currentNote.title.toString()
        view.findViewById<TextView>(R.id.daterow).text =currentNote.timestamp.toString()
        return view

    }

}