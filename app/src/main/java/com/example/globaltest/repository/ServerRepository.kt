package com.example.globaltest.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.globaltest.data.ApiService
import com.example.globaltest.data.NextPathResponse
import com.example.globaltest.data.ServerResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

private const val INCREMENT_COUNT = 1

class ServerRepository(private val applicationContext: Context) {

    private val apiServer = object : ApiService {}
    private val disposable = CompositeDisposable()

    private var responseCode: MutableLiveData<String> = MutableLiveData()
    private var timesFetched: MutableLiveData<Int> = MutableLiveData()
    private var count: Int = 0

    fun fetchPathResponse() {
        apiServer.serviceApi().getNextPath()
            .map { fetchNextPathResponses(it.nextPath) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show() }
            .subscribe()

    }

    private fun fetchNextPathResponses(nextPath: String) {
        val disposables = apiServer.loadPath(nextPath).getPath()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    responseCode.value = it.responseCode
                    updateTimesFetched(it)
                    updateResponseCode(it)
                },
                { Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show() }
            )
        disposable.add(disposables)
    }

    private fun updateTimesFetched(it: NextPathResponse) {
        it.count = count + INCREMENT_COUNT
        count += INCREMENT_COUNT
        timesFetched.value = it.count
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