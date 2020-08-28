package com.erwindevtech.wisatareligi.ui.login

import com.erwindevtech.wisatareligi.data.database.PrefsManager
import com.erwindevtech.wisatareligi.data.model.DataLogin
import com.erwindevtech.wisatareligi.data.model.ResponseLogin

interface LoginContract {

    interface Presenter{
        fun doLogin(username: String,password:String)
        fun setPrefs(prefsManager: PrefsManager, dataLogin: DataLogin)

    }

    interface  View{
        fun onSuccess()
        fun initActivity()//init button
        fun initListener()//untuk event misal onclick
        fun onLoading(loading : Boolean)
        fun onResult(responseLogin: ResponseLogin)
        fun showMessage(message: String)
        fun onLoginSucces(message: String)
    }



}