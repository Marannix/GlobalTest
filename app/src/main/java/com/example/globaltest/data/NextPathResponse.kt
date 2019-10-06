package com.example.globaltest.data

import com.google.gson.annotations.SerializedName

data class NextPathResponse(
    val path: String,
    @SerializedName("response_code")
    val responseCode: String,
    var count: Int
)