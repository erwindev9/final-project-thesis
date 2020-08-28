package com.erwindevtech.wisatareligi.ui.detail.model

data class PostResponse(
    val id_wisata: Int,
    val nama: String,
    val username: String,
    val komentar: String,
    val gambar: String,
    val created_at: String
)