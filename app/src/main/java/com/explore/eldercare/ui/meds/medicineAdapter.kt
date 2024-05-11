package com.explore.eldercare.ui.meds

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.explore.eldercare.R
import com.explore.eldercare.ui.models.medicineModel

class medicineAdapter (private val dataList: ArrayList<medicineModel>): RecyclerView.Adapter<medicineAdapter.ViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): medicineAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.med_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: medicineAdapter.ViewHolder, position: Int) {
        val data = dataList[position]

        holder.name.text = data.name
        holder.price.text = data.price
        holder.treatment.text = data.treatment
        if(data.image!=null) {
            Glide.with(holder.itemView.context).load(data.image).into(holder.image)
            //Picasso.get().load(data.image).into(holder.pfp)
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val name : TextView = view.findViewById(R.id.name)
        val price : TextView = view.findViewById(R.id.email)
        val treatment : TextView = view.findViewById(R.id.adress)
        val image : ImageView = view.findViewById(R.id.image)
        val button : Button = view.findViewById(R.id.button)

    }

}