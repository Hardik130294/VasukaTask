package com.hardik.challengearraylistfilter.data.remote.dto


import com.google.gson.annotations.SerializedName

data class Collections(
    @SerializedName("smart")
    val smart: List<Smart> = listOf(),
    @SerializedName("user")
    val user: List<User> = listOf(),
    @SerializedName("curated")
    val curated: List<Any> = listOf()
)