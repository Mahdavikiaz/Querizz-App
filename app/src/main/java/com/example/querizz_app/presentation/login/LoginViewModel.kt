package com.example.querizz_app.presentation.login

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

    private val _saveSessionStatus = MutableLiveData<LoginResult>()
    val saveSessionStatus: LiveData<LoginResult> = _saveSessionStatus

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    sealed class LoginResult {
        data class Success(val response: String): LoginResult()
        data class Error(val error: String): LoginResult()
        object Loading: LoginResult()
    }

    fun login(email: String, password: String) {
        _isLoading.value = true
        viewModelScope.launch {
            _saveSessionStatus.value = LoginResult.Loading
            try {
                val response = repository.login(email, password)
                _saveSessionStatus.value = LoginResult.Success(response.message ?: "Logged in")
            } catch (e: Exception) {
                _saveSessionStatus.value = LoginResult.Error(e.message ?: "Failed to login")
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                val errorMessage = errorBody.message
                _saveSessionStatus.value = errorMessage?.let {
                    LoginResult.Error(it)
                }
            } finally {
                _isLoading.value = false
            }
        }
    }

}