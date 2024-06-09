package com.example.querizz_app.presentation.add

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.querizz_app.R
import com.example.querizz_app.databinding.ActivityAddSumBinding
import com.example.querizz_app.presentation.result.ResultActivity

class AddSumActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddSumBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddSumBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.fileUpload.setOnClickListener {

        }

        binding.summarize.setOnClickListener {
            startActivity(Intent(this, ResultActivity::class.java))
            finish()
        }
    }
}