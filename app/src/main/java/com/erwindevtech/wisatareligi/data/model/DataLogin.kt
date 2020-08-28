package com.erwindevtech.wisatareligi.data.model

import com.google.gson.annotations.SerializedName

data class DataLogin(

    @SerializedName("user_id") val user_id : Int,
    @SerializedName("username") val username : String,
    @SerializedName("password") val password : String,
    @SerializedName("nama") val nama : String,
    @SerializedName("email") val email : String,
    @SerializedName("tanggal_lahir") val tanggal_lahir : String,
    @SerializedName("jenis_kelamin") val jenis_kelamin : String,
    @SerializedName("agama") val agama: String,
    @SerializedName("alamat") val alamat:String,
    @SerializedName("gambar") val gambar : String,
    @SerializedName("level") val level : String

)