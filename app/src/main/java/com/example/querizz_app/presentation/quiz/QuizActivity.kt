package com.example.querizz_app.presentation.quiz

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.querizz_app.R
import com.example.querizz_app.databinding.ActivityQuizBinding
import com.example.querizz_app.presentation.home.HomeActivity

class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        binding.btnSubmit.setOnClickListener {
            submitQuiz()
        }
    }

    private fun submitQuiz() {
        AlertDialog.Builder(this).apply {
            setTitle("Your Result!")
            setMessage("Anda berhasil login!!")
            setPositiveButton("Back") { _, _ ->
                val intent = Intent(context, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
            create()
            show()
        }
    }
}