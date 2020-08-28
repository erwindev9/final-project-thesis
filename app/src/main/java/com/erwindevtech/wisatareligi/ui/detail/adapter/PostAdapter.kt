package com.erwindevtech.wisatareligi.ui.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.erwindevtech.wisatareligi.R
import com.erwindevtech.wisatareligi.data.model.Constant
import com.erwindevtech.wisatareligi.ui.detail.model.PostResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_post.view.*

class PostAdapter (private val listModel: MutableList<PostResponse>): RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    inner class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(postResponse: PostResponse){
            with(itemView){
                val text ="${postResponse.nama}"

                tvText.text = text

                val komentar = "${postResponse.komentar}"
                tvKomentar.text = komentar

                val date = "${postResponse.created_at}"
                txv_tanggal.text = date

                val image: ImageView
                image = itemView.findViewById(R.id.item_image_post)


                Picasso.get().load("${postResponse.gambar}".replace("localhost",Constant.IP_LOCAL)).into(image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post,parent,false)
        return PostViewHolder(view)
    }

    override fun getItemCount() : Int = listModel.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(listModel[position])
    }

    fun updateList(list: List<PostResponse>){
        listModel.clear()
        listModel.addAll(list)
        notifyDataSetChanged()
    }

}