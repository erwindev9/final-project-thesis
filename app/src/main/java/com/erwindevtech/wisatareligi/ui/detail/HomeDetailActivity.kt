package com.erwindevtech.wisatareligi.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.erwindevtech.wisatareligi.R
import com.erwindevtech.wisatareligi.data.model.Constant
import com.erwindevtech.wisatareligi.data.model.home.DataHomeModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home_detail.*
import kotlinx.android.synthetic.main.content_layout.*

class HomeDetailActivity : AppCompatActivity(),OnMapReadyCallback {
    lateinit var data : DataHomeModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_detail)
//        //GetData
//
//        var intent = intent
//        var aTitle = intent.getStringExtra("iTitle")
//        var aDescription = intent.getStringExtra("iDescription")
//
//        var aKabupaten = intent.getStringExtra("iKabupaten")
//        var aKecamatan = intent.getStringExtra("iKecamatan")
//        var aDesa = intent.getStringExtra("iDesa")
//        var aAgama = intent.getStringExtra("iAgama")
//        var aHarga = intent.getStringExtra("iHarga")
//        var aHari = intent.getStringExtra("iHari")
//        var aWaktu = intent.getStringExtra("iWaktu")
//
//
//        var aImageView = intent.getStringExtra("iImageView")
//
//
//
//
//        //set title
//        actionBar?.setTitle(aTitle)
//        a_title.text = aTitle
//        a_description.text = aDescription
//
//        txv_kabupaten.text = aKabupaten
//        txv_kecamatan.text = aKecamatan
//        txv_desa.text = aDesa
//        txv_agama.text = aAgama
//        txv_harga.text = aHarga
//        txv_hari.text = aHari
//        txv_waktu.text = aWaktu
//
//        Picasso.get().load(aImageView.replace("localhost", Constant.IP_LOCAL)).into(imageView)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        var aLatitude = intent.getStringExtra("iLatitude")
        var aLongitude = intent.getStringExtra("iLongitude")
        var aTitle = intent.getStringExtra("iTitle")


        val latLng = LatLng(aLatitude.toDouble(),aLongitude.toDouble())
        googleMap.addMarker(MarkerOptions().position(latLng).title(aTitle))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,12f))
    }
}