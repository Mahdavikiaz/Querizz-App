package com.example.querizz_app.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.querizz_app.data.model.UserModel
import com.example.querizz_app.data.repository.AuthRepository
import com.example.querizz_app.data.repository.Repository
import com.example.querizz_app.data.response.DataItem
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository) : ViewModel() {

    val summaries: LiveData<PagingData<DataItem>> = repository.getSummary().cachedIn(viewModelScope)

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}