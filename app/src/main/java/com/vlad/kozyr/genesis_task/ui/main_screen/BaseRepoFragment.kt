package com.vlad.kozyr.genesis_task.ui.main_screen

import android.content.Context
import androidx.fragment.app.Fragment

abstract class BaseRepoFragment : Fragment() {
    protected var listener: OnRepoClickListener? = null
    protected var logoutListener: OnLogoutListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnRepoClickListener
        logoutListener = context as? OnLogoutListener
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
        logoutListener = null
    }
}