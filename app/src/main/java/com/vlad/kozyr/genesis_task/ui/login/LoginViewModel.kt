package com.vlad.kozyr.genesis_task.ui.login

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vlad.kozyr.genesis_task.R
import com.vlad.kozyr.genesis_task.domain.repo.AuthRepository
import com.vlad.kozyr.genesis_task.domain.usecase.AuthIntentUseCase
import com.vlad.kozyr.genesis_task.domain.usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val authIntentUseCase: AuthIntentUseCase
) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    fun login(uri: Uri, onSuccess: () -> Unit, onError: (error: Throwable) -> Unit) {
        authUseCase.execute(uri)
            .subscribe(
                { onSuccess() },
                { error -> onError.invoke(error) })
    }

    fun getAuthIntent(username: String): Intent {
        return authIntentUseCase.execute(username)
    }

    fun loginDataChanged(username: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }
}