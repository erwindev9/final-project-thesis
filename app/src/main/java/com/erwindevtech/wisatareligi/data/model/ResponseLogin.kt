package com.erwindevtech.wisatareligi.data.model

import com.erwindevtech.wisatareligi.data.model.DataLogin
import com.google.gson.annotations.SerializedName

data class ResponseLogin(
    @SerializedName("status") val status : Boolean,
    @SerializedName("msg") val msg : String,
    @SerializedName("user") val user : DataLogin? //
)