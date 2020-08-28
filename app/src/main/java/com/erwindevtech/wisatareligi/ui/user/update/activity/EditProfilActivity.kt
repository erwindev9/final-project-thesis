    package com.erwindevtech.wisatareligi.ui.user.update.activity

    import android.app.DatePickerDialog
    import androidx.appcompat.app.AppCompatActivity
    import android.os.Bundle
    import android.view.View
    import android.widget.EditText
    import android.widget.ProgressBar
    import android.widget.Toast
    import com.erwindevtech.wisatareligi.R
    import com.erwindevtech.wisatareligi.data.database.PrefsManager
    import com.erwindevtech.wisatareligi.data.model.DataLogin
    import com.erwindevtech.wisatareligi.data.model.ResponseLogin
    import com.erwindevtech.wisatareligi.network.ApiService
    import com.erwindevtech.wisatareligi.ui.user.update.model.EditProfilResponse
    import kotlinx.android.synthetic.main.activity_edit_profil.*
    import kotlinx.android.synthetic.main.activity_profil.*
    import kotlinx.android.synthetic.main.activity_edit_profil.btn_save
    import kotlinx.android.synthetic.main.activity_edit_profil.edt_agama
    import kotlinx.android.synthetic.main.activity_edit_profil.edt_alamat
    import kotlinx.android.synthetic.main.activity_edit_profil.edt_email
    import kotlinx.android.synthetic.main.activity_edit_profil.edt_jenis_kelamin
    import kotlinx.android.synthetic.main.activity_edit_profil.edt_namaLengkap
    import kotlinx.android.synthetic.main.activity_edit_profil.edt_password
    import kotlinx.android.synthetic.main.activity_edit_profil.edt_pickDate
    import kotlinx.android.synthetic.main.activity_edit_profil.edt_username
    import kotlinx.android.synthetic.main.activity_register.*
    import kotlinx.android.synthetic.main.activity_user.*
    import retrofit2.Call
    import retrofit2.Response
    import java.util.*

    class EditProfilActivity : AppCompatActivity() {

        lateinit var prefsManager: PrefsManager
        lateinit var dataLogin: DataLogin

        private lateinit var mProgressBar: ProgressBar

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_edit_profil)
            prefsManager = PrefsManager(this)

            supportActionBar!!.title = "Edit Profil"
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)

            mProgressBar = findViewById(R.id.progress_circular)
            mProgressBar.visibility = View.GONE

            edt_namaLengkap.setText(prefsManager.prefsNama)
            edt_username.setText(prefsManager.prefsUsername)
            edt_email.setText(prefsManager.prefsEmail)
            edt_pickDate.setText(prefsManager.prefsTanggalLahir)
            edt_alamat.setText(prefsManager.prefsAlamat)
            edt_agama.setText(prefsManager.prefsAgama)

            btn_save.setOnClickListener {
                val agama = edt_agama.text.toString()
                val jenis_kelamin = edt_jenis_kelamin.text.toString()


                if (agama != "kristen" && agama != "islam" && agama != "buddha" && agama != "hindu") {
                    edt_agama.error = "Isi Harus dengan kristen,islam,buddha, dan hindu"
                    edt_agama.requestFocus()
                }

                else if(jenis_kelamin != "P" && jenis_kelamin != "L"){
                    edt_jenis_kelamin.error = "Isi Harus P dan L"
                    edt_jenis_kelamin.requestFocus()
                }
                else {
                    editProfil()

                }
            }
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            edt_pickDate.setOnClickListener{
                val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener
                {view, mYear, mMonth, mDay ->
                    edt_pickDate.setText("$mYear-$mMonth-$mDay")
                },year, month,day)
                //show dialog
                datePickerDialog.show()
            }
        }

        fun onLoading(loading: Boolean){
            when(loading){
                true ->mProgressBar.visibility = View.VISIBLE
                false -> mProgressBar.visibility = View.GONE
            }
        }

        private fun editProfil(){
            val name = edt_namaLengkap.text.toString()
            val email = edt_email.text.toString()
            val username = edt_username.text.toString()
            val password = edt_password.text.toString()
            val jenis_kelamin = edt_jenis_kelamin.text.toString()
            val agama = edt_agama.text.toString()
            val alamat = edt_alamat.text.toString()
            val tanggal_lahir = edt_pickDate.text.toString()



            ApiService.endpoint.edtiProfil(
                prefsManager.prefsUserId,name,email,username,password,jenis_kelamin,agama,alamat,tanggal_lahir
            ).enqueue(object : retrofit2.Callback<EditProfilResponse>{
                override fun onFailure(call: Call<EditProfilResponse>, t: Throwable) {
                    mProgressBar.visibility = View.GONE
                    Toast.makeText(this@EditProfilActivity,"Gagal Menghubungkan ke server", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<EditProfilResponse>,
                    response: Response<EditProfilResponse>
                ) {
                    Toast.makeText(this@EditProfilActivity,response.body()?.msg, Toast.LENGTH_SHORT).show()
                    prefsManager.prefsNama = name
                    prefsManager.prefsEmail = email
                    prefsManager.prefsUsername = username
                    prefsManager.prefsPassword = password
                    prefsManager.prefsJk = jenis_kelamin
                    prefsManager.prefsAgama = agama
                    prefsManager.prefsAlamat = alamat
                    prefsManager.prefsTanggalLahir = tanggal_lahir

                    onLoading(false)



                }

            })


        }


        override fun onSupportNavigateUp(): Boolean {
            setResult(1)
            finish()
            return super.onSupportNavigateUp()
        }
    }