package com.erwindevtech.wisatareligi.mymaps.models

import java.io.Serializable

data class Place(
    val title: String,
    val description: String,
    val latitude : Double,
    val longtitude: Double
): Serializable
