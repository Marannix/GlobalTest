package com.example.globaltest.repository

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.globaltest.data.ApiService
import com.example.globaltest.data.NextPathResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ServerRepository(private val applicationContext: Context) {

    private val apiServer = object : ApiService {}
    private val disposables = CompositeDisposable()
    private var responseCode: MutableLiveData<String> = MutableLiveData()
    private var timesFetched: MutableLiveData<Int> = MutableLiveData()
    private lateinit var counterPreferences: SharedPreferences

    fun fetchPathResponse() {
        val disposable = apiServer.serviceApi().getNextPath()
            .map { fetchNextPathResponses(it.nextPath) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, { Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show() })
        disposables.add(disposable)
    }

    private fun fetchNextPathResponses(nextPath: String) {
        val disposable = apiServer.loadPath(nextPath).getPath()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    updateTimesFetched()
                    updateResponseCode(it)
                },
                { Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show() }
            )
        disposables.add(disposable)
    }

    // TODO: Find a way to extract shared preferences from method, placed inside here because test is failing
    private fun updateTimesFetched() {
        counterPreferences = applicationContext.getSharedPreferences("COUNTER_KEY", 0)
        var counter = counterPreferences.getInt("COUNTER_KEY", 0)
        counter++
        counterPreferences.edit().putInt("COUNTER_KEY", counter).apply()
        timesFetched.value = counter
    }

    private fun updateResponseCode(it: NextPathResponse) {
        responseCode.value = it.responseCode
    }

    fun getResponseCode(): MutableLiveData<String> {
        return responseCode
    }

    fun getTimesFetched(): MutableLiveData<Int> {
        return timesFetched
    }

    fun clearDisposables() {
        disposables.clear()
    }
}