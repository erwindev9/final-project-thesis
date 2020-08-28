package com.erwindevtech.wisatareligi.ui.user

import com.erwindevtech.wisatareligi.data.database.PrefsManager

class UserPresenter (val view: UserContract.View):UserContract.Presenter{

    init {
//        view.initActivity()
        view.initListener()
    }

    override fun doLogin(prefsManager: PrefsManager) {
        if (prefsManager.prefsIsLogin) view.onResultLogin(prefsManager)
    }

    override fun doLogOut(prefsManager: PrefsManager) {

        prefsManager.logout()
        view.showMessage("Berhasil Keluar")
        view.onResultLogout()

    }

}