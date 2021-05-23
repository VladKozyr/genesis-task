package com.vlad.kozyr.genesis_task.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.vlad.kozyr.genesis_task.R
import com.vlad.kozyr.genesis_task.core.afterTextChanged
import com.vlad.kozyr.genesis_task.databinding.ActivityLoginBinding
import com.vlad.kozyr.genesis_task.ui.main_screen.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = binding.username
        val login = binding.login
        val loading = binding.loading

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString()
            )
        }

        login.setOnClickListener {
            loading.visibility = View.VISIBLE
            val intent = loginViewModel.getAuthIntent(username.text.toString())
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val data = intent.data
        if (data != null)
            loginViewModel.login(data,
                { startActivity(Intent(this, MainActivity::class.java)) },
                { showLoginFailed(R.string.something_goes_wrong) })
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}