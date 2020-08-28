package com.erwindevtech.wisatareligi.ui.home

import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.erwindevtech.wisatareligi.R
import com.erwindevtech.wisatareligi.data.database.PrefsManager
import com.erwindevtech.wisatareligi.data.model.Constant
import com.erwindevtech.wisatareligi.network.ApiService
import com.erwindevtech.wisatareligi.ui.detail.adapter.PostAdapter
import com.erwindevtech.wisatareligi.ui.detail.model.PostResponse
import com.erwindevtech.wisatareligi.ui.detail.model.PostResponseList
import com.erwindevtech.wisatareligi.ui.detail.model.post_comment.CreatePostResponse
import com.erwindevtech.wisatareligi.ui.home.get_detail.model.DetailResponseModel
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home_detail.*
import kotlinx.android.synthetic.main.activity_home_map.*
import kotlinx.android.synthetic.main.content_layout.*
import retrofit2.Call
import retrofit2.Response

class HomeMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var latitude:Double= 0.toDouble()
    private  var longitude:Double=0.toDouble()

    private lateinit var mLasLocation: Location
    private var mMarker: Marker?=null

    //location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback

    private lateinit var mapFragment: SupportMapFragment

    //list comment
    private var list= ArrayList<PostResponse>()

    //adapter
    private  var mAdapter= PostAdapter(arrayListOf())

    //prefManager
    lateinit var prefsManager: PrefsManager

    //progress bar
    private lateinit var mProgressBar: ProgressBar

    companion object{
        private const val MY_PERMISSION_CODE: Int = 1000
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_map)

        supportActionBar!!.title = "Detail Wisata"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //prefManager
        prefsManager = PrefsManager(this)

        //progress bar
        mProgressBar = findViewById(R.id.progress_circular)

        //GetData
        var intent = intent
        var aIdWisata = intent.getIntExtra("iIdWisata",0)
        var aTitle = intent.getStringExtra("iTitle")
        var aDescription = intent.getStringExtra("iDescription")

        var aKabupaten = intent.getStringExtra("iKabupaten")
        var aKecamatan = intent.getStringExtra("iKecamatan")
        var aDesa = intent.getStringExtra("iDesa")
        var aAgama = intent.getStringExtra("iAgama")
        var aHarga = intent.getStringExtra("iHarga")
        var aHari = intent.getStringExtra("iHari")
        var aWaktu = intent.getStringExtra("iWaktu")

        var aImageView = intent.getStringExtra("iImageView")


        //set title and other
        actionBar?.setTitle(aTitle)
        a_title.text = aTitle
        a_description.text = aDescription

        txv_kabupaten.text = aKabupaten
        txv_kecamatan.text = aKecamatan
        txv_desa.text = aDesa
        txv_agama.text = aAgama
        txv_harga.text = aHarga
        txv_hari.text = aHari
        txv_waktu.text = aWaktu

        Picasso.get().load(aImageView.replace("localhost", Constant.IP_LOCAL)).into(imageView)
        Picasso.get().load(aImageView.replace("localhost", Constant.IP_LOCAL)).into(imageView)

        //Set Recycler View
        rvPost.setHasFixedSize(true)
        rvPost.layoutManager = LinearLayoutManager(this)

        //function  RCV
        getItemComment()

        //function postComment
        btn_comment.setOnClickListener {
            postComment()
        }

        getDetail()

        //check





        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //Request runtime permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){


            if (checkLocationPermission()){
                buildLocationRequest()
                buildLocationCallback()
                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
                fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,
                    Looper.myLooper())
            }
            else{
                buildLocationRequest()
                buildLocationCallback()
                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
                fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,
                    Looper.myLooper())
            }
        }



        //Edit Text Check
        edt_komentar.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val koment = edt_komentar.text

                if (koment.isNullOrEmpty()){
                    edt_komentar.error = "Tidak Boleh Kosong"
                    btn_comment.isEnabled = false
                    edt_komentar.requestFocus()
                }
                else{
                    btn_comment.isEnabled = true
                }

            }

        })

    }

    private fun getDetail() {
        var aIdWisata = intent.getIntExtra("iIdWisata",0)
        var apicall = ApiService.endpoint.getDetail(aIdWisata,prefsManager.prefsUserId)
        apicall.enqueue(object : retrofit2.Callback<DetailResponseModel>{
            override fun onFailure(call: Call<DetailResponseModel>, t: Throwable) {
                Toast.makeText(this@HomeMapActivity,"Error",Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(
                call: Call<DetailResponseModel>,
                response: Response<DetailResponseModel>
            ) {

            }

        })
    }

    //function pos comment
    private fun postComment() {
        var aIdWisata = intent.getIntExtra("iIdWisata",0)
        val komentar = edt_komentar.text.toString()

        var apicall = ApiService.endpoint.createComment(
            prefsManager.prefsUserId,
            aIdWisata,
            komentar
        ).enqueue(object : retrofit2.Callback<CreatePostResponse>{
            override fun onFailure(call: Call<CreatePostResponse>, t: Throwable) {
                tvResponseCodeComment.text = t.message
            }

            override fun onResponse(
                call: Call<CreatePostResponse>,
                response: Response<CreatePostResponse>
            ) {
                Toast.makeText(this@HomeMapActivity,response.body()?.msg,Toast.LENGTH_SHORT).show()
                getItemComment()
                mProgressBar.visibility = GONE
            }

        })
    }

    private fun getItemComment() {
        var aIdWisata = intent.getIntExtra("iIdWisata",0)

        var apicall = ApiService.endpoint.getComment(aIdWisata)
        apicall.enqueue(object : retrofit2.Callback<PostResponseList>{
            override fun onFailure(call: Call<PostResponseList>, t: Throwable) {
                Toast.makeText(this@HomeMapActivity,"Gagal Terhubung Ke Server",Toast.LENGTH_SHORT).show()
                mProgressBar.visibility = View.GONE
            }

            override fun onResponse(
                call: Call<PostResponseList>,
                response: Response<PostResponseList>
            ) {

                val listResponse = response.body()?.data

                if (listResponse == null){
                    Log.e("null", response.body().toString())
                    val text = response.body()?.msg.toString()
                    tvMessage.text = text
                    mProgressBar.visibility = View.GONE
                }


                else{
                    listResponse?.let { list.addAll(it) }

//                    val adapter = PostAdapter(list)
                    list = response.body()?.data!!
                    mAdapter.updateList(list)
                    rvPost.adapter = mAdapter
                    tvMessage.visibility = View.GONE
                    mProgressBar.visibility = View.GONE
                }

            }

        })
    }

    private fun buildLocationCallback() {
        locationCallback = object : LocationCallback(){
            override fun onLocationResult(p0: LocationResult?) {
                mLasLocation = p0!!.locations.get(p0!!.locations.size-1) //Get last location

                if (mMarker != null){
                    mMarker!!.remove()
                }

                latitude = mLasLocation.latitude
                longitude = mLasLocation.longitude

                var latLng = LatLng(latitude,longitude)
                val markerOptions = MarkerOptions().position(latLng).title("Position Anda").icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                mMarker = mMap!!.addMarker(markerOptions)

                //Move Camera
                mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                mMap!!.animateCamera(CameraUpdateFactory.zoomTo(11f))

            }
        }
    }

    private fun buildLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 3000
        locationRequest.smallestDisplacement =10f
    }

    private fun checkLocationPermission():Boolean {
        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),MY_PERMISSION_CODE)
            }
            else
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),MY_PERMISSION_CODE)

            return false
        }
        else
            return true
    }

    //override on Request
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode)
        {

            MY_PERMISSION_CODE->{
                if (grantResults.size>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                        if (checkLocationPermission()) {
                            buildLocationRequest()
                            buildLocationCallback()
                            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
                            fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,
                                Looper.myLooper())
                            mMap!!.isMyLocationEnabled=true
                        }
                        else{
                            Toast.makeText(this,"Permission Denied", Toast.LENGTH_SHORT).show()
                        }
                }
            }

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onStop() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback )
        super.onStop()
    }




    override fun onMapReady(googleMap: GoogleMap) {
        var intent = intent
        var aLatitude = intent.getStringExtra("iLatitude")
        var aLongitude = intent.getStringExtra("iLongitude")
        var aTitle = intent.getStringExtra("iTitle")

        mMap = googleMap


        // Add a marker in Sydney and move the camera
        val latLng = LatLng(aLatitude.toDouble(), aLongitude.toDouble())
        mMap.addMarker(MarkerOptions().position(latLng).title(aTitle))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,12f))

        //init Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                mMap!!.isMyLocationEnabled = true
            }

        }
        else
            mMap!!.isMyLocationEnabled = true

        //enable zomm control
        mMap.uiSettings.isZoomControlsEnabled = true
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}