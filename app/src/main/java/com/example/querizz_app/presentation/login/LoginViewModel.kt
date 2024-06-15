package com.example.querizz_app.presentation.login

import android.os.UserHandle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.querizz_app.data.model.UserModel
import com.example.querizz_app.data.repository.AuthRepository
import com.example.querizz_app.data.response.ErrorResponse
import com.example.querizz_app.presentation.register.RegisterViewModel
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(private val repository: AuthRepository): ViewModel() {

    private val _saveSessionStatus = MutableLiveData<Boolean>()
    val saveSessionStatus: LiveData<Boolean> = _saveSessionStatus

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    sealed class LoginResult {
        data class Success(val response: String): LoginResult()
        data class Error(val error: String): LoginResult()
        object Loading: LoginResult()
    }

    fun login(name: String, email: String) = repository.login(name, email)

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            try {
                _saveSessionStatus.postValue(true)
                repository.saveSession(user)
            } catch (e: Exception) {
                _saveSessionStatus.postValue(false)
            }
        }
    }

}