package com.example.querizz_app.presentation.result

import android.app.ProgressDialog.show
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.querizz_app.R
import com.example.querizz_app.data.response.DataItem
import com.example.querizz_app.data.response.ResultsItem
import com.example.querizz_app.databinding.ActivityResultBinding
import com.example.querizz_app.presentation.home.HomeActivity
import com.example.querizz_app.presentation.quiz.QuizActivity

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val results = intent.getStringExtra("SUMMARY_RESULTS")

        binding.tvSummary.text = results

        binding.btnGetSummarize.setOnClickListener {
            val intent = Intent(this@ResultActivity, QuizActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}