package com.example.querizz_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.querizz_app.data.model.QuizModel
import com.example.querizz_app.databinding.ItemQuizBinding

class QuizAdapter(private val questionList: ArrayList<QuizModel>) : RecyclerView.Adapter<QuizAdapter.listViewHolder>() {

    class listViewHolder(var binding: ItemQuizBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): listViewHolder {
        val binding = ItemQuizBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return listViewHolder(binding)
    }

    override fun getItemCount(): Int = questionList.size

    override fun onBindViewHolder(holder: listViewHolder, position: Int) {
        val (question, a_answer, b_answer, c_answer, d_answer, correct_answer) = questionList[position]
        holder.binding.tvQuestion.text = question
        holder.binding.aAnswer.text = a_answer
        holder.binding.bAnswer.text = b_answer
        holder.binding.cAnswer.text = c_answer
        holder.binding.dAnswer.text = d_answer
    }

}