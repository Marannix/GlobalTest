package com.example.globaltest.data

import com.google.gson.annotations.SerializedName

data class ServerResponse (
    @SerializedName("next_path")
    val nextPath: String
)