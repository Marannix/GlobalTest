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
    private val disposable = CompositeDisposable()
    private var responseCode: MutableLiveData<String> = MutableLiveData()
    private var timesFetched: MutableLiveData<Int> = MutableLiveData()
    private var counterPreferences: SharedPreferences = applicationContext.getSharedPreferences("COUNTER_KEY", 0)
    private var edit: SharedPreferences.Editor = counterPreferences.edit()

    fun fetchPathResponse() {
        val disposables = apiServer.serviceApi().getNextPath()
            .map { fetchNextPathResponses(it.nextPath) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, { Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show() })
        disposable.add(disposables)
    }

    private fun fetchNextPathResponses(nextPath: String) {
        val disposables = apiServer.loadPath(nextPath).getPath()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    responseCode.value = it.responseCode
                    updateTimesFetched()
                    updateResponseCode(it)
                },
                { Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show() }
            )
        disposable.add(disposables)
    }

    private fun updateTimesFetched() {
        var counter = counterPreferences.getInt("counts", 0)
        counter++
        edit.putInt("COUNTER_KEY", counter)
        edit.commit()
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

}