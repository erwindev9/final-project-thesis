package com.erwindevtech.wisatareligi.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.erwindevtech.wisatareligi.ui.user.register.UserCreateActivity
import com.erwindevtech.wisatareligi.R
import com.erwindevtech.wisatareligi.data.database.PrefsManager
import com.erwindevtech.wisatareligi.data.model.DataLogin
import com.erwindevtech.wisatareligi.data.model.ResponseLogin
import com.erwindevtech.wisatareligi.ui.home.HomeActivity
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginContract.View {

    lateinit var presenter: LoginPresenter
    lateinit var prefsManager: PrefsManager
    lateinit var dataLogin: DataLogin
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        onLoading(false)
        presenter = LoginPresenter(this)
        prefsManager = PrefsManager(this)





    }

    override fun onSuccess() {
        /*val intent = Intent(this,HomeActivity::class.java)
        startActivity(intent)*/
    }

    override fun initActivity() {
        val username = edtUsername.text
        val password = edtPassword.text
        supportActionBar!!.title = "Masuk"
        btnLogin.setOnClickListener {
            if (username.isNullOrEmpty()) {
                edtUsername.error = "Tidak boleh kosong"
                edtUsername.requestFocus()
            } else if (password.isNullOrEmpty()) {
                edtPassword.error = "Tidak boleh kosong"
                edtPassword.requestFocus()
            } else {
                presenter.doLogin(
                    username.toString(), password.toString()
                )

                onSuccess()
            }

        }
    }


    override fun initListener() {
        btn_register.setOnClickListener{
            startActivity(Intent(this,
                UserCreateActivity::class.java))
        }

        /*btnLogin.setOnClickListener {
            presenter.doLogin(edtUsername.text.toString(), edtPassword.text.toString())
            onSuccess()

        }*/

    }

    override fun onResult(responseLogin: ResponseLogin) {
//        Log.d("LoginActivity" ,"responseLogin: ${responseLogin.user}")
        responseLogin.user?.let { presenter.setPrefs(prefsManager, it) }
        finish()

    }

    override fun onLoading(loading : Boolean){
        when(loading){
            true ->{
                progress.visibility = View.VISIBLE
                btnLogin.visibility = View.GONE
            }
            false ->{
                progress.visibility = View.GONE
                btnLogin.visibility = View.VISIBLE
            }
        }
    }


   override fun showMessage(message:String){
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onLoginSucces(message: String) {
        startActivity(Intent(this@LoginActivity,HomeActivity::class.java))
        Toasty.success(this,message,Toast.LENGTH_SHORT).show()
    }


}
