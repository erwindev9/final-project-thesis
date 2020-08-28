package com.erwindevtech.wisatareligi.ui.user.update

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.erwindevtech.wisatareligi.*
import com.erwindevtech.wisatareligi.Activity.Model.ModelSpinner
import com.erwindevtech.wisatareligi.Activity.Model.ModelSpinnerJenisKelamin
import com.erwindevtech.wisatareligi.data.database.PrefsManager
import com.erwindevtech.wisatareligi.data.model.register.ResponseUserUpdate
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_profil.*
import kotlinx.android.synthetic.main.bottom_sheet_layout.view.*
import java.util.*
import kotlin.collections.ArrayList

class UserUpdateActivity : AppCompatActivity(),UserUpdateContract.View {

    var dataList = ArrayList<ModelSpinner>()
    var dataList_JK = ArrayList<ModelSpinnerJenisKelamin>()
    private val PERMISSION_CODE: Int = 1000;
    private val IMAGE_CAPTURE_CODE: Int = 1001;
    private val IMAGE_PICK_CODE = 1000
    var image_uri: Uri? = null



    lateinit var prefsManager: PrefsManager
    lateinit var presenter: UserUpdatePresenter
    lateinit var image_View: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)

        presenter = UserUpdatePresenter(this)
        presenter.doLogin(prefsManager)

        supportActionBar!!.title = "Edit Profile"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

//        image_View=findViewById(R.id.image_view)

        //Open Bottom Sheet Dialog
        val bottomSheetDialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_layout, null)
        bottomSheetDialog.setContentView(view)

//        img_open.setOnClickListener {
//            val close = view.findViewById<ImageView>(R.id.iv_close)
//            close.setOnClickListener {
//                bottomSheetDialog.dismiss()
//            }
//
//            bottomSheetDialog.setCancelable(false)
//            bottomSheetDialog.show()
//        }

        //Open Camera
        view.btn_camera.setOnClickListener {
            Toast.makeText(this, "Klik Camera", Toast.LENGTH_SHORT).show()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED
                ) {
                    //Permission was not enable
                    val permission = arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    //show pop up to request permission
                    requestPermissions(permission, PERMISSION_CODE)
                } else {
                    //Permission already granted
                    openCamera()
                }
            } else {
                //System OS is < marsmellow
                openCamera()
            }
        }

        //Open Gallery
        view.btn_gallery.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
                ) {
                    //Permission denied
                    val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    //Show pop up to request runtime permission
                    requestPermissions(permission, PERMISSION_CODE)
                } else {
                    //permission already granted
                    pickImageGallery()
                }
            } else {
                pickImageGallery()
            }
        }

        //Button Delete
        view.btn_delete.setOnClickListener {

        }

        //Calendar
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        //Date Time Picker
//        edt_pickDate.setOnClickListener{
//            val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener
//            {view, mYear, mMonth, mDay ->
//                edt_pickDate.setText("$mDay / $mMonth / $mYear")
//            },year, month,day)
//            //show dialog
//            datePickerDialog.show()
//        }
    }

    private fun pickImageGallery() {
        //Intent to pick Image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Pictures")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From The Camera")
        image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        //CAMERA INTENT
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                )
                //Permission form pop up was granted
                    openCamera()
                else {
                    //Permission form pop up was granted
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
            }
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_DENIED
                )
                //Permission Denied
                    pickImageGallery()
                else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        /*if (resultCode == Activity.RESULT_OK) {
            image_view.setImageURI(image_uri)
        }
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            image_view.setImageURI(data?.data)
        }*/

    }

    override fun initActivity() {
        supportActionBar!!.title = "Edit Profile"
        supportActionBar!!.elevation = 0f
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun initListener() {
        /*btn_save.setOnClickListener {

        }*/
    }



    override fun onLoading(loading: Boolean) {
       /* when ( loading ) {
            true -> {
                progress.visibility = View.VISIBLE
                btn_save.visibility = View.GONE
            }
            false -> {
                progress.visibility = View.GONE
                btn_save.visibility = View.VISIBLE
            }
        }*/
    }

    override fun onResultLogin(prefsManager: PrefsManager) {
//        Picasso.get().load(prefsManager.prefsGambar?.replace("localhost", Constant.IP_LOCAL)).into(image_View)
        /*edt_namaLengkap.setText(prefsManager.prefsNama)
        edt_username.setText(prefsManager.prefsUsername)
        edt_email.setText(prefsManager.prefsEmail)
        edt_pickDate.setText(prefsManager.prefsTanggalLahir)
        edt_alamat.setText(prefsManager.prefsAlamat)
        edt_agama.setText(prefsManager.prefsAgama)*/
    }

    override fun onResultLogout() {
        finish()
    }

    override fun onResultUpdate(responseUserUpdate: ResponseUserUpdate) {
        showMessage(responseUserUpdate.msg)
    }

    override fun showMessage(message: String) {
        Toast.makeText(applicationContext, message,Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }


}
