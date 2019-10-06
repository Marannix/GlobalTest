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

    private val serverRepository = ServerRepository(application)

    fun getPathResponse() {
        serverRepository.fetchPathResponse()
    }

    fun getResponseCode(): MutableLiveData<String> {
        return serverRepository.getResponseCode()
    }

    fun getTimesFetched(): MutableLiveData<Int> {
        return serverRepository.getTimesFetched()
    }
}