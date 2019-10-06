package com.example.globaltest.api

import com.example.globaltest.data.NextPathResponse
import com.example.globaltest.data.ServerResponse
import io.reactivex.Single
import retrofit2.http.GET

interface ServiceApi {

    @GET(" ")
    fun getNextPath() : Single<ServerResponse>

    @GET(" ")
    fun getPath() : Single<NextPathResponse>
}