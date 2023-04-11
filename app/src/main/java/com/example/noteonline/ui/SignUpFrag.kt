package com.example.noteonline.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.noteonline.R
import com.example.noteonline.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth


class SignUpFrag : Fragment() {
    lateinit var binding: FragmentSignUpBinding
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        saveDateOnFireBase()
    }

    private fun saveDateOnFireBase() {
        binding.save.setOnClickListener {
            val addressSignUp = binding.editTextEmail.text.toString()
            val passwordSignUp = binding.editTextPassword.text.toString()
            binding.progressBar.visibility = View.VISIBLE
            if (addressSignUp.isNotEmpty() && passwordSignUp.isNotEmpty()) {
                firebaseAuth.createUserWithEmailAndPassword(addressSignUp, passwordSignUp)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(requireContext(), "Successful", Toast.LENGTH_LONG).show()
                            verifiction()
                        } else {
                            binding.progressBar.visibility = View.INVISIBLE
                            Toast.makeText(
                                requireContext(),
                                it.exception.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            } else {
                binding.progressBar.visibility = View.INVISIBLE
                Toast.makeText(
                    requireContext(),
                    "Please Enter Your Email And Password",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }


    private fun verifiction() {
        val currentUser = firebaseAuth.currentUser
        currentUser?.sendEmailVerification()?.addOnCompleteListener {
            if (it.isSuccessful) {
                findNavController().navigate(R.id.action_signUpFrag_to_logInFrag)
            } else {
                Toast.makeText(requireContext(), it.exception.toString(), Toast.LENGTH_LONG).show()
            }
        }

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