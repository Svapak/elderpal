package com.explore.eldercare.ui.contacts

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.explore.eldercare.MainActivity
import com.explore.eldercare.R
import com.explore.eldercare.ui.contacts.retrofit.DoctorList


class DoctorListAdapter(val fragment: Fragment): RecyclerView.Adapter<DoctorListAdapter.MyViewHolder>() {

    private var doctorList: List<DoctorList>? = null

    fun setDoctorList(doctorList: List<DoctorList>?){
        this.doctorList = doctorList
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DoctorListAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_doctor, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: DoctorListAdapter.MyViewHolder, position: Int) {
        holder.bind(doctorList?.get(position)!!,fragment)
    }

    override fun getItemCount(): Int {
        if(doctorList == null)return 0
        else return doctorList?.size!!
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var pfp : ImageView = itemView.findViewById(R.id.pfp)
        var username : TextView= itemView.findViewById(R.id.username)
        var email : TextView= itemView.findViewById(R.id.email)
        var experience: TextView = itemView.findViewById(R.id.experience)
        var specialization :TextView= itemView.findViewById(R.id.specialization)
        var description:TextView = itemView.findViewById(R.id.description)

        fun bind(data: DoctorList, fragment: Fragment){
            username.text = data.username
            email.text = data.email
            experience.text = data.experience
            specialization.text = data.specialization
            description.text = data.description

            Glide.with(fragment).load(data.image).placeholder(R.drawable.images)
                .into(pfp)
        }
    }

}