package com.example.globaltest.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.globaltest.repository.ServerRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ServerViewModel(application: Application) : AndroidViewModel(application) {

    private var responseCode: MutableLiveData<String> = MutableLiveData()
    private var timesFetched: MutableLiveData<Int> = MutableLiveData()
    private var count: Int = 0

    private val context = application.applicationContext
    private val serverRepository = ServerRepository(application)
    private val disposables = CompositeDisposable()

    // TODO: Extract this to repository
    fun fetchPathResponse() {
        val disposable = serverRepository.fetchServer()
            .map { fetchNextPathResponse(it.nextPath) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show() }
            .subscribe()
        disposables.add(disposable)
    }

    // TODO: Extract this to repository
    // TODO: Times fetched should be created and incremented (Room?)
    private fun fetchNextPathResponse(nextPath: String) {
        val disposable = serverRepository.fetchNextPathResponse(nextPath)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    it.count = count + 1
                    count += 1
                    responseCode.value = it.responseCode
                    timesFetched.value = it.count
                    Log.d("count", it.count.toString())
                },
                { Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show() }
            )

        disposables.add(disposable)
    }

    fun getResponseCode(): MutableLiveData<String> {
        return responseCode
    }

    fun getTimesFetched(): MutableLiveData<Int> {
        return timesFetched
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}