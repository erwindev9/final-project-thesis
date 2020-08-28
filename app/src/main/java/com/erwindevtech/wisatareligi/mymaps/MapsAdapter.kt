package com.erwindevtech.wisatareligi.mymaps

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.erwindevtech.wisatareligi.R
import com.erwindevtech.wisatareligi.mymaps.models.LocationMap
import kotlinx.android.synthetic.main.item_location_map.view.*

private const val TAG = "MasAdapter"

class MapsAdapter(val context: Context, val userMaps: List<LocationMap>, val onClickListener: OnClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    interface OnClickListener{
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_location_map,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount() = userMaps.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val userMap = userMaps[position]
        holder.itemView.setOnClickListener {
            Log.i(TAG,"Tapped on position $position")
            onClickListener.onItemClick(position)
        }
        val textViewTitle = holder.itemView.findViewById<TextView>(R.id.tvMapTitle)
        textViewTitle.text =userMap.title
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}