package com.erwindevtech.wisatareligi.ui.user.update

import com.erwindevtech.wisatareligi.data.database.PrefsManager
import com.erwindevtech.wisatareligi.data.model.register.ResponseUserUpdate
import java.io.File

interface UserUpdateContract {
    interface Presenter{
        fun doLogin(prefsManager: PrefsManager)
        fun doLogOut(prefsManager: PrefsManager)
        fun updateUser(user_id: Int,name: String,email: String,username: String
                       ,password: String,jenis_kelamin:String
                       ,agama: String,alamat: String,tanggal_lahir: String)
    }

    interface View{
        fun initActivity()
        fun initListener()
        fun onLoading(loading : Boolean)
        fun onResultLogin(prefsManager: PrefsManager)
        fun onResultLogout()
        fun onResultUpdate(responseUserUpdate: ResponseUserUpdate)
        fun showMessage(message: String)
    }

}