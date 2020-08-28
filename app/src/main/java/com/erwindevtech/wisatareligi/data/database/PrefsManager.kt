package com.erwindevtech.wisatareligi.data.database

import android.content.Context
import android.content.SharedPreferences
import hu.autsoft.krate.*

class PrefsManager (context: Context): Krate{

    //VARIABEL UTK KEY
    private val PREFS_IS_LOGIN : String = "prefs_is_login"
    private val PREFS_USER_ID : Int = 3
    private val PREFS_USERNAME :String = "prefs_username"
    private val PREFS_PASSWORD : String = "prefs_password"
    private val PREFS_NAMA : String = "prefs_nama"
    private val PREFS_EMAIL : String = "prefs_email"
    private val PREFS_TANGGAL_LAHIR : String = "prefs_tanggal_lahir"
    private val PREFS_JK : String = "prefs_jk"
    private val PREFS_AGAMA : String = "prefs_agama"
    private val PREFS_ALAMAT : String = "prefs_alamat"
    private val PREFS_GAMBAR : String = "prefs_gambar"
    private val PREFS_LEVEL : String = "prefs_level"

    override val sharedPreferences: SharedPreferences
    init {
        sharedPreferences = context.applicationContext.getSharedPreferences(
            "wisata_religi_prefs12345",Context.MODE_PRIVATE
        )
    }

    //FUNCTION
    var prefsIsLogin by booleanPref(PREFS_IS_LOGIN, false)
    var prefsUserId by intPref("",PREFS_USER_ID)
    var prefsUsername by stringPref(PREFS_USERNAME)
    var prefsPassword by stringPref(PREFS_PASSWORD)
    var prefsNama by stringPref(PREFS_NAMA,"")
    var prefsEmail by stringPref(PREFS_EMAIL,"")
    var prefsTanggalLahir by stringPref(PREFS_TANGGAL_LAHIR)
    var prefsJk by stringPref(PREFS_JK)
    var prefsAgama by stringPref(PREFS_AGAMA)
    var prefsAlamat by stringPref(PREFS_ALAMAT)
    var prefsGambar by stringPref(PREFS_GAMBAR)
    var prefsLevel by stringPref(PREFS_LEVEL)

    fun logout(){
        sharedPreferences.edit().clear().apply()
    }

}