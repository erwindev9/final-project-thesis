package com.erwindevtech.wisatareligi.ui.search.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.erwindevtech.wisatareligi.R
import com.erwindevtech.wisatareligi.data.model.Constant
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_search_detail.*
import kotlinx.android.synthetic.main.content_layout.*

class SearchDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_detail)

        //get data
        var intent = intent
        var aTitle = intent.getStringExtra("iTitle")
        var aDescription = intent.getStringExtra("iDescription")
        var aImageView = intent.getStringExtra("iImageView")
        var aLatitude = intent.getStringExtra("iLatitude")
        var aLongitude = intent.getStringExtra("iLongitude")

        //set title
        actionBar?.setTitle("Detai Wisata Religi")
        a_title.text = aTitle
        a_description.text = aDescription
        Picasso.get().load(aImageView.replace("localhost", Constant.IP_LOCAL)).into(imageView)

    }
}