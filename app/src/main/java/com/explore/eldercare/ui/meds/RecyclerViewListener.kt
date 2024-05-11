package com.explore.eldercare.ui.meds

import android.view.View
import com.explore.eldercare.ui.healthcare.HealthcareData

interface RecyclerViewListener {

    fun onRecyclerViewItemClick(view: View, user: HealthcareData,x: String){

    }
}