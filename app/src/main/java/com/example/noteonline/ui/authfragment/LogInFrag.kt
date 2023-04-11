package com.example.noteonline.ui.authfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.noteonline.R
import com.example.noteonline.databinding.FragmentLogInBinding
import com.google.firebase.auth.FirebaseAuth


class LogInFrag : Fragment() {
    lateinit var binding: FragmentLogInBinding
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogInBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        openSignUpFrag()
        saveInfoAndNavToHome()
    }

    private fun saveInfoAndNavToHome() {
        binding.signIn.setOnClickListener {
            val siEmail = binding.signInEmail.text.toString()
            val siPassword = binding.signInpassword.text.toString()
            if (siEmail.isNotEmpty() && siPassword.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(siEmail, siPassword).addOnCompleteListener {
                    if (it.isSuccessful) {
                        verification()
                    } else {
                        Toast.makeText(requireContext(), it.exception.toString(), Toast.LENGTH_LONG)
                            .show()
                    }

                }
            } else {
                Toast.makeText(requireContext(), "Failed", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun verification() {
        val currentUser = firebaseAuth.currentUser
        if (currentUser!!.isEmailVerified) {
            findNavController().navigate(R.id.action_logInFrag_to_homeFrag)
        } else {
            Toast.makeText(requireContext(), "Try Again", Toast.LENGTH_LONG).show()
        }
    }

    private fun openSignUpFrag() {
        binding.SignUp.setOnClickListener {
            findNavController().navigate(R.id.action_logInFrag_to_signUpFrag)
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