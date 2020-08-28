package com.erwindevtech.wisatareligi.data.model.home

import com.erwindevtech.wisatareligi.R
import kotlinx.android.synthetic.main.activity_home.*


data class DataModelResponseHome(
    val data: ArrayList<DataHomeModel>,
    val sampleImages: IntArray = intArrayOf(
        R.drawable.img_pexels,
        R.drawable.ic_action_search
    )
)