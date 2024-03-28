package com.hardik.challengearraylistfilter.data.remote.dto


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("index")
    val index: List<Index> = listOf(),
    @SerializedName("collections")
    val collections: Collections = Collections()
)