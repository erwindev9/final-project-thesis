package com.erwindevtech.wisatareligi.ui.home.get_detail.model

data class DetailResponseModel(
    val id_wisata : String,
    val nama_wisata: String,
    val deskripsi: String,
    val latitude: String,
    val longitude: String,
    val kabupaten: String,
    val kecamatan: String,
    val desa: String,
    val usia: String,
    val agama: String,
    val waktu: String,
    val hari : String
)