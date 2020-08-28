    package com.erwindevtech.wisatareligi.ui.user

    import android.content.Intent
    import android.os.Bundle
    import android.widget.ImageView
    import android.widget.Toast
    import androidx.appcompat.app.AppCompatActivity
    import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
    import com.erwindevtech.wisatareligi.R
    import com.erwindevtech.wisatareligi.data.database.PrefsManager
    import com.erwindevtech.wisatareligi.data.model.Constant
    import com.erwindevtech.wisatareligi.data.model.DataLogin
    import com.erwindevtech.wisatareligi.ui.login.LoginActivity
    import com.erwindevtech.wisatareligi.ui.user.update.activity.EditProfilActivity
    import com.squareup.picasso.Picasso
    import kotlinx.android.synthetic.main.activity_user.*


    class UserActivity : AppCompatActivity(),UserContract.View {
        lateinit var prefsManager: PrefsManager
        lateinit var presenter: UserPresenter
        lateinit var imageView: ImageView
        lateinit var dataLogin: DataLogin

        private lateinit var swipe : SwipeRefreshLayout

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_user)

            swipe = findViewById(R.id.srl_data)

            imageView = findViewById(R.id.imvUser)

            prefsManager = PrefsManager(this)
            presenter = UserPresenter(this)
            presenter.doLogin(prefsManager)


            swipe.setOnRefreshListener {
                onLoadingSwipe(false)
                presenter.doLogin(prefsManager)

            }

        }

        fun onLoadingSwipe(loading: Boolean){
            when(loading){
                true -> swipe.isRefreshing = true
                false -> swipe.isRefreshing =false
            }
        }

        override fun onStart() {
            super.onStart()

        }

        override fun initActivity() {
            supportActionBar!!.title = "Masuk"
            supportActionBar!!.hide()
        }

        override fun initListener() {
            btnBack.setOnClickListener {
                finish()
            }
            txvLogout.setOnClickListener {

                presenter.doLogOut(prefsManager)


            }
            btnEditProfil.setOnClickListener {

                val i = Intent(this, EditProfilActivity::class.java)
                startActivityForResult(i, 1)

    //            val dialog = AlertDialog.Builder(this)
    //            val dialogView = layoutInflater.inflate(R.layout.custom_dialog2, null)
    //            dialog.setView(dialogView)
    //            dialog.setCancelable(false)
    //            dialog.setPositiveButton("Login", { dialog: DialogInterface?, which: Int -> })
    //            dialog.setNegativeButton("Batal",{ dialog: DialogInterface?, which: Int ->  })
    //            val customDialog = dialog.create()
    //            customDialog.show()
    //            customDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
    //                customDialog.dismiss()
    //                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.gmail.com"))
    //                startActivity(intent)
    //
    //            }
    //            customDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener {
    //                customDialog.dismiss()
    //            }
            }


        }

        override fun onLoading(loading: Boolean) {
            TODO("Not yet implemented")
        }

        override fun onResultLogin(prefsManager: PrefsManager) {
            Picasso.get().load(prefsManager.prefsGambar?.replace("localhost",Constant.IP_LOCAL)).into(imageView)
            txvUsername.text = prefsManager.prefsUsername
            txvName.text = prefsManager.prefsNama
            txvGender.text = prefsManager.prefsJk
            txvAddress.text = prefsManager.prefsAlamat
            txvAgama.text = prefsManager.prefsAgama
            txvEmail.text = prefsManager.prefsEmail
            txvTanggalLahir.text = prefsManager.prefsTanggalLahir
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            presenter.doLogin(prefsManager)


        }

        override fun onResultLogout() {
            finish()
            startActivity(Intent(this, LoginActivity::class.java))
        }

        override fun showMessage(message: String) {
            Toast.makeText(applicationContext, message,Toast.LENGTH_SHORT).show()
        }
    }