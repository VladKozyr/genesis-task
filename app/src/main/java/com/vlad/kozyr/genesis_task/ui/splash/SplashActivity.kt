package com.vlad.kozyr.genesis_task.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.vlad.kozyr.genesis_task.R
import com.vlad.kozyr.genesis_task.domain.repo.AuthRepository
import com.vlad.kozyr.genesis_task.ui.login.LoginActivity
import com.vlad.kozyr.genesis_task.ui.main_screen.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val intent = if (authRepository.isLoggedIn())
            Intent(this, MainActivity::class.java) else
            Intent(this, LoginActivity::class.java)

        startActivity(intent)
    }
}