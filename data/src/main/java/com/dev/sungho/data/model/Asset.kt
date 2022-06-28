package com.dev.sungho.data.model

import com.google.gson.annotations.SerializedName

data class Asset(
    @SerializedName("id")
    val id: Long?,

    @SerializedName("url")
    val url: String?,

    @SerializedName("headline")
    val headline: String?,

    @SerializedName("theAbstract")
    val theAbstract: String?,

    @SerializedName("byLine")
    val byLine: String?,

    @SerializedName("timeStamp")
    val timeStamp: Long?,

    @SerializedName("relatedImages")
    val relatedImages: List<RelatedImage>?
)