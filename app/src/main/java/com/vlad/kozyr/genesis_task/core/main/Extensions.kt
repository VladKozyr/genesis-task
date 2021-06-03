package com.vlad.kozyr.genesis_task.core.main

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import com.vlad.kozyr.genesis_task.core.network.*
import io.reactivex.rxjava3.core.Single
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

fun <T> Single<T>.errorMap(): Single<T> =
    this.onErrorResumeNext { error ->
        when (error) {
            is SocketTimeoutException -> Single.error(NoNetworkError())
            is UnknownHostException -> Single.error(ServerUnreachable())
            is HttpException -> {
                when (error.code()) {
                    401 -> Single.error(UnauthorizedError())
                    500 -> Single.error(InternalServerError())
                    else -> Single.error(NetworkError())
                }
            }
            else -> Single.error(Error("Undefined error"))
        }
    }
