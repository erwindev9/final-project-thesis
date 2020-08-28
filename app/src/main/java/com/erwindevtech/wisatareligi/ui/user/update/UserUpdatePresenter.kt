package com.erwindevtech.wisatareligi.ui.user.update

import com.erwindevtech.wisatareligi.data.database.PrefsManager
import com.erwindevtech.wisatareligi.data.model.register.ResponseUserUpdate
import com.erwindevtech.wisatareligi.network.ApiService
import com.erwindevtech.wisatareligi.ui.user.UserContract
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class UserUpdatePresenter(val view: UserUpdateContract.View):UserUpdateContract.Presenter {
    init {
        view.initListener()
        view.onLoading(false)
        view.initActivity()
    }

    override fun doLogin(prefsManager: PrefsManager) {
        if (prefsManager.prefsIsLogin) view.onResultLogin(prefsManager)
    }

    override fun doLogOut(prefsManager: PrefsManager) {
        prefsManager.logout()
        view.showMessage("Berhasil Keluar")
        view.onResultLogout()
    }

    override fun updateUser(
        user_id: Int,
        name: String,
        email: String,
        username: String,
        password: String,
        jenis_kelamin: String,
        agama: String,
        alamat: String,
        tanggal_lahir: String
    ) {
        view.onLoading(true)
        ApiService.endpoint.updateUser(user_id,name,email,username,password,jenis_kelamin,agama,alamat,tanggal_lahir,"PUT").enqueue(object : Callback<ResponseUserUpdate>{
            override fun onFailure(call: Call<ResponseUserUpdate>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(
                call: Call<ResponseUserUpdate>,
                response: Response<ResponseUserUpdate>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseUser: ResponseUserUpdate? = response.body()
                    view.onResultUpdate(responseUser!!)
                }
            }

        })
    }






}