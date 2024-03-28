package com.hardik.challengearraylistfilter.data.remote.dto


import com.google.gson.annotations.SerializedName

data class Smart(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("label")
    val label: String = "",
    @SerializedName("smart")
    val smart: String = "",
    @SerializedName("courses")
    val courses: List<Int> = listOf(),
    @SerializedName("is_default")
    val isDefault: Int = 0,
    @SerializedName("is_archive")
    val isArchive: Int = 0,
    @SerializedName("description")
    val description: String = ""
)