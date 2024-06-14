package com.example.querizz_app.presentation.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.querizz_app.databinding.ActivityHomeBinding
import com.example.querizz_app.presentation.add.AddSumActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.fabUpload.setOnClickListener {
            navigateToUpload()
        }
    }

    private fun navigateToUpload() {
        val intent = Intent(this@HomeActivity, AddSumActivity::class.java)
        startActivity(intent)
        finish()
    }
}