package com.vlad.kozyr.genesis_task.core

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class SessionManager @Inject constructor(
    context: Context
) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)

    var isActive: Boolean = sharedPreferences.contains(KEY_USER)
        private set

    fun saveUser() {
        sharedPreferences.edit()
            .putBoolean(KEY_USER, true)
            .apply()
    }

    fun deleteUser() {
        sharedPreferences.edit()
            .remove(PREF_NAME)
            .apply()
    }

    companion object {
        const val PREF_NAME = "session_pref"
        const val KEY_USER = "user"
        const val PRIVATE_MODE = 0
    }
}