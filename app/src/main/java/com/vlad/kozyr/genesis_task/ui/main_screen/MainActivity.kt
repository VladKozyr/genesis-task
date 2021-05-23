package com.vlad.kozyr.genesis_task.ui.main_screen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.vlad.kozyr.genesis_task.R
import com.vlad.kozyr.genesis_task.databinding.ActivityMainBinding
import com.vlad.kozyr.genesis_task.domain.repo.AuthRepository
import com.vlad.kozyr.genesis_task.ui.login.LoginActivity
import com.vlad.kozyr.genesis_task.ui.main_screen.history.HistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnRepoClickListener {

    private val historyViewModel: HistoryViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = (supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
        NavigationUI.setupWithNavController(binding.navBar, navController)
    }

    override fun onRepoClick(repo: RepoView) {
        historyViewModel.addToHistory(repo)
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(repo.url)
        }
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.option -> authRepository.signOut()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { completed ->
                    if (completed) startActivity(Intent(this, LoginActivity::class.java))
                }
        }
        return super.onOptionsItemSelected(item)
    }
}