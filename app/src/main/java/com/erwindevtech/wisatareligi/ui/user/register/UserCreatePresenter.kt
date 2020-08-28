package com.erwindevtech.wisatareligi.ui.user.register

import com.erwindevtech.wisatareligi.data.model.register.ResponseUserUpdate
import com.erwindevtech.wisatareligi.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserCreatePresenter(val view: UserCreateContract.View):UserCreateContract.Presenter {
    init {
        view.initActivity()
        view.initListener()
        view.onLoading(false)
    }

    override fun insertUser(
        name: String,
        email: String,
        username: String,
        password: String,
        password2: String,
        agama: String
    ) {
        view.onLoading(true)
        ApiService.endpoint.insertUser(name, email, username, password, password2,agama)
            .enqueue(object :Callback<ResponseUserUpdate>{
                override fun onFailure(call: Call<ResponseUserUpdate>, t: Throwable) {
                    view.onLoading(false)
                }

                override fun onResponse(
                    call: Call<ResponseUserUpdate>,
                    response: Response<ResponseUserUpdate>
                ) {
                    view.onLoading(false)
                    if (response.isSuccessful) {
                        val responseUser: ResponseUserUpdate? = response.body()
                        view.onResult(responseUser!!)
                    }
                }

            })
    }

}