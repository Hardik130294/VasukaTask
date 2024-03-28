package com.hardik.challengearraylistfilter.data.remote.dto


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("label")
    val label: String = "",
    @SerializedName("is_default")
    val isDefault: Int = 0,
    @SerializedName("is_archive")
    val isArchive: Int = 0,
    @SerializedName("description")
    val description: String = "",
    @SerializedName("courses")
    val courses: List<Int> = listOf()
)