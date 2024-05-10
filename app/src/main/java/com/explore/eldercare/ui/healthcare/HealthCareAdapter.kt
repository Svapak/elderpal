package com.explore.eldercare.ui.healthcare

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.explore.eldercare.R
import com.explore.eldercare.databinding.HealthcareListItemBinding

class HealthCareAdapter(private var list : List<HealthcareData>): RecyclerView.Adapter<HealthCareAdapter.ViewHolder>() {
    inner class ViewHolder(val binding : HealthcareListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = HealthcareListItemBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val healthcareData = list[position]
        holder.binding.apply {
            tvAgeHealthcare.text = healthcareData.age
            tvNameHealthcare.text = healthcareData.name
            tvEmailHealthcare.text = healthcareData.email
            tvAddressHealthcare.text = healthcareData.address
            tvExperienceHealthcare.text = healthcareData.experience
            Glide.with(holder.itemView.context).load(healthcareData.image).placeholder(R.drawable.images)
                .into(ivHealthcare)

        }
    }

    fun setData(newData: List<HealthcareData>) {
        list = newData
        notifyDataSetChanged()
    }

}