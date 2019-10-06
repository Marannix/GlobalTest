package com.example.globaltest.data

import com.example.globaltest.api.ServiceApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "http://10.0.2.2:8000"

interface ApiService {

    private fun provideRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun provideServiceApi(retrofit: Retrofit): ServiceApi {
        return retrofit.create(ServiceApi::class.java)
    }

    fun serviceApi(): ServiceApi {
        return provideServiceApi(provideRetrofit())
    }

}