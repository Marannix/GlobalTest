package com.example.globaltest.repository

import android.content.Context
import android.widget.Toast
import com.example.globaltest.data.ApiService
import com.example.globaltest.data.NextPathResponse
import com.example.globaltest.data.ServerResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ServerRepository(private val applicationContext: Context) {

    private val apiServer = object : ApiService {}
    lateinit var path: String
    lateinit var responseCode: String

//    fun fetchServer() : Disposable {
//        return apiServer.serviceApi().getNextPath()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {
//                    fetchNextPathResponse(it.nextPath)
//                    Toast.makeText(applicationContext, it.nextPath, Toast.LENGTH_SHORT).show()
//                },
//                {
//                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
//                }
//            )
//    }

//    private fun fetchNextPathResponse(nextPath: String) : Disposable {
//        return apiServer.loadPath(nextPath).getPath()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {
//                    path = it.path
//                    responseCode = it.responseCode
//                },
//                {
//                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
//                }
//            )
//    }

    fun fetchServer() : Single<ServerResponse> {
        return apiServer.serviceApi().getNextPath()
    }

    fun fetchNextPathResponse(nextPath: String) : Single<NextPathResponse> {
        return apiServer.loadPath(nextPath).getPath()
    }
}