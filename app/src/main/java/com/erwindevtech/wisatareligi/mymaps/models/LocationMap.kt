package com.erwindevtech.wisatareligi.mymaps.models

import java.io.Serializable

data class LocationMap(
    val title: String,
    val places:List<Place>
): Serializable