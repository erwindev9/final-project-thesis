package com.erwindevtech.wisatareligi.data.model.register

import com.google.gson.annotations.SerializedName

data class ResponseUserUpdate(
    @SerializedName("status") val status: Boolean,
    @SerializedName("msg") val msg: String
)