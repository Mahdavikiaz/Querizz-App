package com.example.querizz_app.presentation.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.querizz_app.data.di.Injection
import com.example.querizz_app.data.repository.Repository
import com.example.querizz_app.presentation.add.AddSumViewModel

class ViewModelFactory(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AddSumViewModel::class.java) -> {
                AddSumViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        fun getInstance(context: Context) =
            ViewModelFactory(Injection.provideRepository(context))
    }
}