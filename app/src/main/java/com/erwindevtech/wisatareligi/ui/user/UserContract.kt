package com.erwindevtech.wisatareligi.ui.user

import com.erwindevtech.wisatareligi.data.database.PrefsManager

interface UserContract {
    interface Presenter{
        fun doLogin(prefsManager: PrefsManager)
        fun doLogOut(prefsManager: PrefsManager)
    }

    interface View{
        fun initActivity()
        fun initListener()
        fun onLoading(loading : Boolean)
        fun onResultLogin(prefsManager: PrefsManager)
        fun onResultLogout()
        fun showMessage(message: String)
    }
}