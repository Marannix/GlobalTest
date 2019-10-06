package com.example.globaltest.repository

import com.example.globaltest.data.ApiService
import com.example.globaltest.data.ServerResponse
import io.reactivex.Single

class ServerRepository() {

    private val apiServer = object : ApiService {}

    fun fetchServer() : Single<ServerResponse> {
        return apiServer.serviceApi().getNextPath()
    }
}