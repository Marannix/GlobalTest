package com.example.globaltest.viewmodel

import android.app.Application
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
    private val serverRepository = ServerRepository(application)

    private val context = application.applicationContext
    private val disposables = CompositeDisposable()

    // TODO: Extract this to repository

    fun getServer() {
        val disposable = serverRepository.fetchServer().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    fetchNextPathResponse(it.nextPath)
                    Toast.makeText(context, it.nextPath, Toast.LENGTH_SHORT).show()
                },
                {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            )
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
                    responseCode.value = it.responseCode
                },
                {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            )
        disposables.add(disposable)
    }

    fun getResponseCode(): MutableLiveData<String> {
        return responseCode
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}