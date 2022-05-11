package com.example.nyt_mostpopular

import com.google.gson.annotations.SerializedName

data class Media(
    var type:String,
    @SerializedName("media-metadata") var mediaMetadata: List<MediaMetadata>
)
