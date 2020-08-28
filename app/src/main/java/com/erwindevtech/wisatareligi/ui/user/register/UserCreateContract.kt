package com.erwindevtech.wisatareligi.ui.user.register

import com.erwindevtech.wisatareligi.data.model.register.ResponseUserUpdate

interface UserCreateContract {
    interface Presenter{
        fun insertUser(
            name: String,email: String,username: String,password: String,password2: String,agama: String
        )
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean)
        fun onResult(responseUserUpdate: ResponseUserUpdate)
        fun showMessage(message: String)
    }
}