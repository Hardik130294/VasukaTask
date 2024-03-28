package com.hardik.challengearraylistfilter.data.remote.dto


import com.google.gson.annotations.SerializedName

data class ItemResponseDto(
    @SerializedName("result")
    val result: Result = Result()
)