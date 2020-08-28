package com.erwindevtech.wisatareligi.ui.login

import android.content.Intent
import android.widget.Toast
import com.erwindevtech.wisatareligi.data.database.PrefsManager
import com.erwindevtech.wisatareligi.data.model.DataLogin
import com.erwindevtech.wisatareligi.data.model.ResponseLogin
import com.erwindevtech.wisatareligi.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPresenter(val view : LoginContract.View): LoginContract.Presenter{

    lateinit var intent: Intent

    init {
        view.initActivity()
        view.initListener()
        view.onLoading(false)
        view.onSuccess()

    }

    override fun doLogin(username: String, password: String) {
        view.onLoading(true)
        ApiService.endpoint.loginUser(username,password).enqueue(object : Callback<ResponseLogin> {
            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                view.onLoading(false)

            }

            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                view.onLoading(false)
                if (response.isSuccessful){
                    val responseLogin : ResponseLogin? = response.body()
                    view.showMessage(responseLogin!!.msg)

                    if (responseLogin!!.status) {
                        view.onResult(responseLogin)
                        view.onLoginSucces("Login Sukses")
                    }


                }

            }

        })
    }

    override fun setPrefs(prefsManager: PrefsManager, dataLogin: DataLogin) {
        prefsManager.prefsIsLogin = true
        prefsManager.prefsUserId = dataLogin.user_id
        prefsManager.prefsUsername = dataLogin.username
        prefsManager.prefsPassword = dataLogin.password
        prefsManager.prefsNama = dataLogin.nama
        prefsManager.prefsEmail = dataLogin.email
        prefsManager.prefsTanggalLahir = dataLogin.tanggal_lahir
        prefsManager.prefsJk = dataLogin.jenis_kelamin
        prefsManager.prefsAgama = dataLogin.agama
        prefsManager.prefsAlamat = dataLogin.alamat
        prefsManager.prefsGambar = dataLogin.gambar
        prefsManager.prefsLevel = dataLogin.level
    }
}