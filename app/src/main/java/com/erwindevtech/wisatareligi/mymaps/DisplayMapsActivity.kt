package com.erwindevtech.wisatareligi.mymaps

import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.erwindevtech.wisatareligi.R
import com.erwindevtech.wisatareligi.mymaps.models.LocationMap
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

private const val TAG = "DisplayMapActivity"
class DisplayMapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var userMap: LocationMap

    private var latitude:Double= 0.toDouble()
    private  var longitude:Double=0.toDouble()

    private lateinit var mLasLocation: Location
    private var mMarker: Marker?=null

    //location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback

    lateinit var mapFragment: SupportMapFragment

    companion object{
        private const val MY_PERMISSION_CODE: Int = 1000
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_maps)

        userMap = intent.getSerializableExtra(EXTRA_USER_MAP) as LocationMap
        supportActionBar?.title = userMap.title

        supportActionBar?.title = userMap.title
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

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
        mMap = googleMap
/*
        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))*/



        Log.i(TAG,"User Map to render: ${userMap.title}")
        val boundsBuilder = LatLngBounds.Builder()
        for (place in userMap.places){

            val latLng = LatLng(place.latitude, place.longtitude)
            boundsBuilder.include(latLng)
            mMap.addMarker(MarkerOptions().position(latLng).title(place.title).snippet(place.description))

        }
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(),2000,2000,0))

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