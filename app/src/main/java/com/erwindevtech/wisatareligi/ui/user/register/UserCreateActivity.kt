package com.erwindevtech.wisatareligi.ui.user.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.erwindevtech.wisatareligi.R
import com.erwindevtech.wisatareligi.data.model.register.ResponseUserUpdate
import com.erwindevtech.wisatareligi.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_register.*

class UserCreateActivity : AppCompatActivity(),UserCreateContract.View {
    lateinit var presenter:UserCreatePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        presenter = UserCreatePresenter(this)


    }

    override fun initActivity() {
        supportActionBar!!.title = "Daftar"
        supportActionBar!!.elevation = 0f
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun initListener() {
        btnSubmit.setOnClickListener {
            val name = edtFullName.text
            val email = edtEmail.text
            val agama = edtAgama.text
            val username = edtUsername.text
            val password = edtPassword.text
            val password2 = edtPassword2.text

            if (name.isNullOrEmpty()){
                edtFullName.error = "Tidak Boleh Kosong"
                edtFullName.requestFocus()
            }
            else if (username.isNullOrEmpty()){
                edtUsername.error = "Tidak Boleh Kosong"
                edtUsername.requestFocus()
            }
            else if (email.isNullOrEmpty()){
                edtEmail.error = "Tidak Boleh Kosong"
                edtEmail.requestFocus()
            }
            else if (agama.isNullOrEmpty()){
                edtAgama.error = "Tidak Boleh Kosong"
                edtAgama.requestFocus()
            }
            else if (agama.toString() != "kristen" && agama.toString() != "islam" && agama.toString() != "buddha" && agama.toString() != "hindu") {
                edtAgama.error = "Isi Harus dengan kristen,islam,buddha, dan hindu"
                edtAgama.requestFocus()
            }

            else if (password.isNullOrEmpty()){
                edtPassword.error = "Tidak Boleh Kosong"
                edtPassword.requestFocus()
            }
            else if (password2.isNullOrEmpty()){
                edtPassword2.error = "Tidak Boleh Kosong"
                edtPassword2.requestFocus()
            }
            else{
                presenter.insertUser(name.toString(),email.toString(),username.toString(),agama.toString(), password.toString(), password2.toString())
            }
        }

        tv_login.setOnClickListener {
            startActivity(Intent(this,
                LoginActivity::class.java))
        }
    }

    override fun onLoading(loading: Boolean) {
        when(loading){
            true -> {
                progress.visibility = View.VISIBLE
                btnSubmit.visibility = View.GONE
            }
            false -> {
                progress.visibility = View.GONE
                btnSubmit.visibility = View.VISIBLE
            }
        }
    }

    override fun onResult(responseUserUpdate: ResponseUserUpdate) {
        showMessage(responseUserUpdate.msg)
    }

    override fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
