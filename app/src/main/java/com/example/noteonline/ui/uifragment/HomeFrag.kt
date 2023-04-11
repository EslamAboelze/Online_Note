package com.example.noteonline.ui.uifragment

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.noteonline.R
import com.example.noteonline.adapter.NoteRecylerAdapetr
import com.example.noteonline.databinding.FragmentHomeBinding
import com.example.noteonline.model.Note
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class HomeFrag : Fragment() {
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var binding: FragmentHomeBinding
    lateinit var mref: DatabaseReference
    lateinit var mNoteList: ArrayList<Note>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mNoteList = ArrayList()
        firebaseAuth = FirebaseAuth.getInstance()
        actionOnFloating()
        getInstanceFromRealTime()

    }


    private fun showData() {
        mref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mNoteList.clear()
                for (n in snapshot.children) {
                    val vnote = n.getValue(Note::class.java)
                    mNoteList.add(0, vnote!!)
                }
                val noteRecylerAdapter =
                    NoteRecylerAdapetr(requireContext(), mNoteList, this@HomeFrag, mref)
                binding.listOfNote.setHasFixedSize(true)
                binding.listOfNote.adapter = noteRecylerAdapter
                binding.listOfNote.layoutManager=StaggeredGridLayoutManager(2,LinearLayout.VERTICAL)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun actionOnFloating() {
        var manager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var networkInfo = manager.getActiveNetworkInfo()
        if (networkInfo != null && networkInfo.isConnected()) {
            binding.addNewNote.setOnClickListener {
                openNormalDialogue()
            }
        }else{
            Toast.makeText(requireContext(),"Please Connect your NetWork",Toast.LENGTH_LONG).show()
        }
    }

    private fun openNormalDialogue() {
        val alertBuilder = AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.normal_dialogue, null)
        alertBuilder.setView(view)
        val alertDialog = alertBuilder.create()
        alertDialog.show()
        val btnAdd = view.findViewById<Button>(R.id.adding)
        val titleEntered = view.findViewById<EditText>(R.id.titleOfNote).text
        val contentEntered = view.findViewById<EditText>(R.id.content_of_note).text
        btnAdd.setOnClickListener {
            if (titleEntered.isNotEmpty() && contentEntered.isNotEmpty()) {
                val id = mref!!.push().key.toString()
                val note =
                    Note(id, titleEntered.toString(), contentEntered.toString(), getCurrentData())
                mref.child(id).setValue(note)
                alertDialog.dismiss()
            }
            else {
                Toast.makeText(requireContext(), "Empty", Toast.LENGTH_LONG).show()
            }
        }
        }



    //method for get current date
    private fun getCurrentData(): String {
        val calendar = Calendar.getInstance()
        val mformat = SimpleDateFormat("EEEE hh:mm a")
        val strdate = mformat.format(calendar.time)
        return strdate
    }

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser == null) {
            findNavController().navigate(R.id.action_homeFrag_to_logInFrag)
        }
        showData()
    }

    private fun getInstanceFromRealTime() {
        val database = FirebaseDatabase.getInstance()
        mref = database.getReference("Note")

    }


}