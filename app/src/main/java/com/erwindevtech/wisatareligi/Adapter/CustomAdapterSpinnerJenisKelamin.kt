package com.erwindevtech.wisatareligi.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.erwindevtech.wisatareligi.Activity.Model.ModelSpinnerJenisKelamin
import com.erwindevtech.wisatareligi.R
import kotlinx.android.synthetic.main.spinner_item_data_jenis_kelamin.view.*

class CustomAdapterSpinnerJenisKelamin :BaseAdapter {
    lateinit var context : Context
    lateinit var dataSpinner : ArrayList<ModelSpinnerJenisKelamin>

    constructor(context: Context, dataSpinner: ArrayList<ModelSpinnerJenisKelamin>) : super() {
        this.context = context
        this.dataSpinner = dataSpinner
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view = inflater.inflate(R.layout.spinner_item_data_jenis_kelamin,null)
        view.text.text = dataSpinner[position].jenis_kelamin

        return view

    }

    override fun getItem(position: Int): Any {
        return dataSpinner[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return dataSpinner.size
    }

}