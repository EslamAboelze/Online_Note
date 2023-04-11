package com.example.noteonline.ui.noteactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.noteonline.databinding.ActivitySplashBinding

class Splash : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadingSplashScreen()
    }

    private fun loadingSplashScreen() {
        val intent = Intent(this, MainActivity::class.java)
        val t: Thread = object : Thread() {
            override fun run() {
                super.run()
                try {
                    sleep(3000)
                    startActivity(intent)
                    finish()

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        }
        t.start()
    }


}