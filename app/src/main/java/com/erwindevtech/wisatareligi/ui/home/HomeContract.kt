package com.erwindevtech.wisatareligi.ui.home

import com.erwindevtech.wisatareligi.data.database.PrefsManager

interface HomeContract {
    interface Presenter{
        fun doLogin(prefsManager: PrefsManager)
        fun doLogout(prefsManager: PrefsManager)
    }

    interface View{
        fun onResultLogin(prefsManager: PrefsManager)

    }
}