package com.example.locationservice.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.locationservice.databinding.LocRecLytBinding
import com.example.locationservice.room.Entity

class MyLocationsAdapter(val list: ArrayList<Entity>): RecyclerView.Adapter<MyLocationsAdapter.MyVH>() {

    inner class MyVH(val binding: LocRecLytBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVH =
        MyVH(LocRecLytBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: MyVH, position: Int) {
        holder.binding.apply {
            list[position].let {
                latLngTxt.text = "LAT: ${it.lat} \nLNG: ${it.lng}"
                TimeTxt.text = it.time
                airplaneTxt.text = if (it.airplaneMode) "ON" else "OFF"
            }
        }
    }

    override fun getItemCount(): Int = list.size

}