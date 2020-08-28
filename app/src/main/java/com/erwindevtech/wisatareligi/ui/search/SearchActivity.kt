package com.erwindevtech.wisatareligi.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.erwindevtech.wisatareligi.Adapter.MyAdapter
import com.erwindevtech.wisatareligi.R
import com.erwindevtech.wisatareligi.data.model.search.DataModel
import com.erwindevtech.wisatareligi.data.model.search.DataModelResponse
import com.erwindevtech.wisatareligi.network.ApiService
import retrofit2.Call
import retrofit2.Response

class SearchActivity : AppCompatActivity() {
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mProgressBar: ProgressBar
    private lateinit var itemList: MutableList<DataModel>
    private lateinit var mAdapter: MyAdapter
    private lateinit var editTextSearch : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        mRecyclerView = findViewById(R.id.recyclerview_home)
        mProgressBar = findViewById(R.id.progress_circular)
        editTextSearch = findViewById(R.id.et_search)

        supportActionBar!!.title = "Cari"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        getItemList()

        editTextSearch.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                filterList(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

    }

    private fun filterList(filterItem: String) {
        var tempList: MutableList<DataModel> = ArrayList()

        for (d in itemList){
            if (filterItem.toLowerCase() in d.nama_wisata.toLowerCase()){
                tempList.add(d)
            }
        }

        mAdapter.updateList(tempList as ArrayList<DataModel>)
    }

    private fun getItemList() {
        var apicall = ApiService.endpoint.getData()
        apicall.enqueue(object : retrofit2.Callback<DataModelResponse>{
            override fun onFailure(call: Call<DataModelResponse>, t: Throwable) {
                mProgressBar.visibility = View.GONE
                Toast.makeText(this@SearchActivity,"Gagal Menghubungkan ke server", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<DataModelResponse>,
                response: Response<DataModelResponse>
            ) {
                if(response.isSuccessful){
                    itemList = response.body()!!.data
                    mAdapter = MyAdapter(itemList as ArrayList<DataModel>,this@SearchActivity)
                    val mlayoutManager = LinearLayoutManager(this@SearchActivity)
                    mRecyclerView.layoutManager = mlayoutManager
                    mRecyclerView.adapter = mAdapter
                    mProgressBar.visibility = View.GONE
                    editTextSearch.isEnabled = true
                }
            }

        })
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}