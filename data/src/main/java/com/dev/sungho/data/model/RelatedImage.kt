package com.dev.sungho.data.model

import com.google.gson.annotations.SerializedName

data class RelatedImage(
    @SerializedName("id")
    val id: Long?,

    @SerializedName("url")
    val url: String?,

    @SerializedName("width")
    val width: Int?,

    @SerializedName("height")
    val height: Int?
)