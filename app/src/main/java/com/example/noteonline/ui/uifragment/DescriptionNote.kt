package com.example.noteonline.ui.uifragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.noteonline.databinding.FragmentDescriptionNoteBinding

class DescriptionNote : Fragment() {
    lateinit var binding :FragmentDescriptionNoteBinding
    lateinit var args: DescriptionNoteArgs
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        args = DescriptionNoteArgs.fromBundle(requireArguments())
        binding = FragmentDescriptionNoteBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.titledes.text = args.title
        binding.contentdes.text = args.content
    }

    override fun onPause() {
        super.onPause()
        (activity as AppCompatActivity).supportActionBar?.show()
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()

    }

}