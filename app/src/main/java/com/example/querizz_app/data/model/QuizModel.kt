package com.example.querizz_app.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuizModel (
    val question: String,
    val a_answer: String,
    val b_answer: String,
    val c_answer: String,
    val d_answer: String,
    val correct_answer: String
    ) : Parcelable