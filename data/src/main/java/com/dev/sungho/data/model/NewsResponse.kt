package com.dev.sungho.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data entity of news articles.
 * We need to make all fields nullable firstly and will verify them
 * when mapping into domain model.
 */
data class NewsResponse(
    @SerializedName("id")
    val id: Long?,

    @SerializedName("displayName")
    val displayName: String?,

    @SerializedName("assets")
    val assets: List<Asset>?,

    @SerializedName("timeStamp")
    val timeStamp: Long?
)