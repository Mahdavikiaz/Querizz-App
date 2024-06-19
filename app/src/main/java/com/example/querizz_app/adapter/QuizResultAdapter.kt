package com.example.querizz_app.adapter

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.querizz_app.R
import com.example.querizz_app.data.model.QuizModel
import com.example.querizz_app.databinding.ItemQuizBinding

class QuizResultAdapter(private val questionList: ArrayList<QuizModel>, private val context: Context) : RecyclerView.Adapter<QuizResultAdapter.listViewHolder>() {

    class listViewHolder(var binding: ItemQuizBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): listViewHolder {
        val binding = ItemQuizBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return listViewHolder(binding)
    }

    override fun onBindViewHolder(holder: listViewHolder, position: Int) {
        val (question, a_answer, b_answer, c_answer, d_answer, correct_answer) = questionList[position]
        holder.binding.tvQuestion.text = question
        holder.binding.aAnswer.text = a_answer
        holder.binding.bAnswer.text = b_answer
        holder.binding.cAnswer.text = c_answer
        holder.binding.dAnswer.text = d_answer
        holder.binding.rgAnswer.isEnabled = false

        val correctColor = context.resources.getDrawable(R.drawable.answer_correct) // Assuming this is your color string

        when (correct_answer) {
            a_answer -> holder.binding.aAnswer.setBackgroundDrawable(correctColor)
            b_answer -> holder.binding.bAnswer.setBackgroundDrawable(correctColor)
            c_answer -> holder.binding.cAnswer.setBackgroundDrawable(correctColor)
            d_answer -> holder.binding.dAnswer.setBackgroundDrawable(correctColor)
        }
    }

    override fun getItemCount(): Int = questionList.size
}