package com.hardik.challengearraylistfilter.data.remote.dto


import com.google.gson.annotations.SerializedName

data class Index(
    @SerializedName("downloadid")
    val downloadid: Int = 0,
    @SerializedName("cd_downloads")
    val cdDownloads: Int = 0,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("status")
    val status: Int = 0,
    @SerializedName("release_date")
    val releaseDate: String = "",
    @SerializedName("authorid")
    val authorid: Int = 0,
    @SerializedName("video_count")
    val videoCount: Int = 0,
    @SerializedName("style_tags")
    val styleTags: List<String> = listOf(),
    @SerializedName("skill_tags")
    val skillTags: List<String> = listOf(),
    @SerializedName("series_tags")
    val seriesTags: List<String> = listOf(),
    @SerializedName("curriculum_tags")
    val curriculumTags: List<String> = listOf(),
    @SerializedName("educator")
    val educator: String = "",
    @SerializedName("owned")
    val owned: Int = 0,
    @SerializedName("sale")
    val sale: Int = 0,
    @SerializedName("purchase_order")
    val purchaseOrder: Any = 0,// or false
    /// val purchaseOrder: PurchaseOrder = PurchaseOrder.IntegerValue(0) // or PurchaseOrder.BooleanValue(false)
    @SerializedName("watched")
    val watched: Int = 0,
    @SerializedName("progress_tracking")
    val progressTracking: Double = 0.0
)