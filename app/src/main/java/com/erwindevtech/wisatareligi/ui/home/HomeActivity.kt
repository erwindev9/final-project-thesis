package com.erwindevtech.wisatareligi.ui.home

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.erwindevtech.wisatareligi.Adapter.MyAdapterHome
import com.erwindevtech.wisatareligi.R
import com.erwindevtech.wisatareligi.data.database.PrefsManager
import com.erwindevtech.wisatareligi.data.model.DataLogin
import com.erwindevtech.wisatareligi.data.model.home.DataHomeModel
import com.erwindevtech.wisatareligi.data.model.home.DataModelResponseHome
import com.erwindevtech.wisatareligi.mymaps.MapActivity
import com.erwindevtech.wisatareligi.network.ApiService
import com.erwindevtech.wisatareligi.ui.MoreActivity
import com.erwindevtech.wisatareligi.ui.login.LoginActivity
import com.erwindevtech.wisatareligi.ui.search.SearchActivity
import com.erwindevtech.wisatareligi.ui.user.UserActivity
import com.squareup.picasso.Picasso
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.activity_home.*
import retrofit2.Call
import retrofit2.Response

class HomeActivity : AppCompatActivity(), View.OnClickListener{

    val sampleImages1 = arrayOf(
        R.drawable.masji_raya,
        R.drawable.masjid_lama,
        R.drawable.gereja_hkbp,
        R.drawable.maha_viara_mahetrya
    )

    val imagesListener = ImageListener{position, imageView ->
        Picasso.get().load((sampleImages1[position])).placeholder(R.drawable.ic_launcher_background).into(imageView)
    }

    lateinit var prefsManager: PrefsManager

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mProgressBar: ProgressBar
    private lateinit var itemList: MutableList<DataHomeModel>
    private lateinit var mAdapter: MyAdapterHome
    private lateinit var editTextSearch : EditText
    private lateinit var imageButton: ImageButton
    private lateinit var dataLogin: DataLogin

    private lateinit var swipe : SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        swipe = findViewById(R.id.srl_data)



        mRecyclerView = findViewById(R.id.recyclerview_home)
        mProgressBar = findViewById(R.id.progress_circular)


        prefsManager = PrefsManager(this)
        imageButton = findViewById(R.id.crvUser)
        var btnIntentMore = findViewById<ImageButton>(R.id.moreBtn)
        var btnMap= findViewById<ImageButton>(R.id.mapBtn)



        //btn event click
        btnMap.setOnClickListener(this)
        imageButton.setOnClickListener(this)
        btnIntentMore.setOnClickListener(this)
//        editTextSearch = findViewById(R.id.et_search)

        //alert dialog



        /*crvUser.setOnClickListener {
            startActivity(Intent(this, UserActivity::class.java))
        }*/

        //cek login

        swipe.setOnRefreshListener {

                swipe.setRefreshing(true)
                getItemList()
                swipe.setRefreshing(false)

        }
        btn_rekomendasi.setOnClickListener {
            if (prefsManager.prefsIsLogin){
//            startActivity(Intent(this,HomeActivity::class.java))

            }
            else{
                Toast.makeText(applicationContext,"Anda Belum Login",Toast.LENGTH_SHORT).show()
                val dialog = AlertDialog.Builder(this)
                val dialogView = layoutInflater.inflate(R.layout.custom_dialog1, null)
                dialog.setView(dialogView)
                dialog.setCancelable(false)
                dialog.setPositiveButton("Login", { dialog: DialogInterface?, which: Int -> })
                dialog.setNegativeButton("Batal",{dialog: DialogInterface?, which: Int ->  })
                val customDialog = dialog.create()
                customDialog.show()
                customDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                    customDialog.dismiss()
                    startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
                }
                customDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener {
                    customDialog.dismiss()
                }
//                val builder = AlertDialog.Builder(this)
//                builder.setTitle("Are You Sure")
//                builder.setMessage("Do You Want To Close The App ?")
//                builder.setPositiveButton("Yes",{dialog: DialogInterface?, which: Int ->
//                    finish()
//                })
//                builder.setNegativeButton("No",{dialog: DialogInterface?, which: Int ->
//                    builder.show()
//                })


            }
        }

        //swipre refresh





        //click item
//        mAdapter.setOnItemClickCallback(object : MyAdapterHome.OnItemClickCallback{
//            override fun onItemClicked(data: DataHomeModel) {
//                Toast.makeText(this@HomeActivity, data.nama_wisata,Toast.LENGTH_SHORT).show()
//            }
//
//        })




        carouselView.pageCount = sampleImages1.size
        carouselView.setImageListener(imagesListener)



    }

    override fun onStart() {
        super.onStart()


    }


    override fun onResume() {
        super.onResume()
        when(prefsManager.prefsIsLogin){
            true->{
                crvUser.visibility = View.VISIBLE
                btn_rekomendasi.visibility = View.GONE
            }
            false->{
                crvUser.visibility =View.GONE
            }
        }
        getItemList()

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu,menu)
        return true
    }


    //opeen search
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.search){
            open()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun  open(){
        val intent = Intent(this, SearchActivity::class.java)
        startActivity(intent)
    }

    private fun getItemList() {
        var aUser_id = intent.getStringExtra("IAgama")
        var apicall = ApiService.endpoint.getDataHome("${prefsManager.prefsAgama}")
        apicall.enqueue(object : retrofit2.Callback<DataModelResponseHome>{
            override fun onFailure(call: Call<DataModelResponseHome>, t: Throwable) {
                mProgressBar.visibility = View.GONE
                Toast.makeText(this@HomeActivity,"Gagal Menghubungkan ke server", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<DataModelResponseHome>,
                response: Response<DataModelResponseHome>
            ) {
                if(response.isSuccessful){
                    itemList = response.body()!!.data
                    mAdapter = MyAdapterHome(itemList as ArrayList<DataHomeModel>,this@HomeActivity)
                    val mlayoutManager = LinearLayoutManager(this@HomeActivity)
                    mRecyclerView.layoutManager = mlayoutManager
                    mRecyclerView.adapter = mAdapter
                    mProgressBar.visibility = View.GONE
                }
            }

        })
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.crvUser -> run{
                val intentProfil = Intent(this@HomeActivity,UserActivity::class.java )
                startActivity(intentProfil)
            }
            R.id.moreBtn ->run{
                val intentMore = Intent(this@HomeActivity,MoreActivity::class.java )
                startActivity(intentMore)
            }
            R.id.mapBtn -> run {
                val intentMaps = Intent(this@HomeActivity,MapActivity::class.java)
                startActivity(intentMaps)
            }
        }
    }


}