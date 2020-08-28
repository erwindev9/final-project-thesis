package com.erwindevtech.wisatareligi.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.erwindevtech.wisatareligi.R
import com.erwindevtech.wisatareligi.data.database.PrefsManager
import com.erwindevtech.wisatareligi.data.model.Constant
import com.erwindevtech.wisatareligi.data.model.home.DataHomeModel
import com.erwindevtech.wisatareligi.data.model.home.DataModelResponseHome
import com.erwindevtech.wisatareligi.data.model.search.DataModel
import com.erwindevtech.wisatareligi.ui.detail.HomeDetailActivity
import com.erwindevtech.wisatareligi.ui.detail.model.PostResponse
import com.erwindevtech.wisatareligi.ui.home.HomeMapActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list.view.*
import kotlinx.android.synthetic.main.item_list_home.view.*
import kotlinx.android.synthetic.main.item_list_home.view.title_text
import kotlinx.android.synthetic.main.item_list_home.view.txt_year

class MyAdapterHome(private var itemlist : ArrayList<DataHomeModel>,val context: Context): RecyclerView.Adapter<MyAdapterHome.CustomViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_home,parent,false)
        return (CustomViewHolder(view))
    }

    override fun getItemCount():Int =itemlist.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        val itemData = itemlist.get(position)
        Picasso.get().load(itemData.gambar.replace("localhost",Constant.IP_LOCAL)).into(holder.img)

        holder.title_text.setText(itemData.nama_wisata)
        holder.txt_year.setText(itemData.kabupaten)

        holder.bindItems(itemlist[position])
        holder.itemView.setOnClickListener {


            //get position of selected item
            val model = itemlist.get(position)

            //get title , description, and other of selected item with intent
            var gId_Wisata: Int = model.id_wisata
            var gTitle : String = model.nama_wisata
            var gDeskripsi : String = model.deskripsi
            var gLatitude : String = model.latitude
            var gLongitude: String = model.longitude

            var gKabupaten: String = model.kabupaten
            var gKecamatan: String = model.kecamatan
            var gDesa: String = model.desa
            var gAgama: String = model.agama
            var gHarga: String = model.harga
            var gHari: String = model.hari
            var gWaktu: String = model.waktu

            //get Image with intent, which position is selected
            var gImageView : String = model.gambar

            //create intent
            val intent = Intent(context,HomeMapActivity::class.java )
            intent.putExtra("iIdWisata",gId_Wisata)
            intent.putExtra("iTitle",gTitle)
            intent.putExtra("iDescription",gDeskripsi)
            intent.putExtra("iImageView",gImageView)
            intent.putExtra("iLatitude",gLatitude)
            intent.putExtra("iLongitude",gLongitude)

            intent.putExtra("iKabupaten",gKabupaten)
            intent.putExtra("iKecamatan",gKecamatan)
            intent.putExtra("iDesa",gDesa)
            intent.putExtra("iAgama",gAgama)
            intent.putExtra("iHarga",gHarga)
            intent.putExtra("iHari",gHari)
            intent.putExtra("iWaktu",gWaktu)

            //start
            context.startActivity(intent)

        }
    }

    inner class CustomViewHolder(view: View): RecyclerView.ViewHolder(view) {

        fun bindItems(dataModel: DataHomeModel){
            itemView.title_text.text = dataModel.nama_wisata
            itemView.txt_year.text=dataModel.kabupaten
        }



        var img: ImageView
        var title_text : TextView
        var txt_year : TextView

        init {
            img =  view.findViewById(R.id.item_image)
            title_text = view.findViewById(R.id.title_text)
            txt_year = view.findViewById(R.id.txt_year)
        }
    }


}