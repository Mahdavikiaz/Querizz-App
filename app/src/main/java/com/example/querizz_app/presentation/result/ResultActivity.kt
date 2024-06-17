package com.example.querizz_app.presentation.result

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.querizz_app.R
import com.example.querizz_app.data.response.DataItem
import com.example.querizz_app.data.response.ResultsItem
import com.example.querizz_app.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val results = intent.getStringArrayListExtra("SUMMARY_RESULTS")

        val chatAdapter = ResultAdapter(results!!)
        binding.rvChat.layoutManager = LinearLayoutManager(this)
        binding.rvChat.adapter = chatAdapter
    }
}