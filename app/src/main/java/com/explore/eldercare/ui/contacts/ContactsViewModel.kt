package com.explore.eldercare.ui.contacts

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.explore.eldercare.ui.contacts.retrofit.DoctorList
import com.explore.eldercare.ui.contacts.retrofit.RetrofitServiceInterface
import com.explore.eldercare.ui.contacts.retrofit.Retroinstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContactsViewModel : ViewModel() {
    lateinit var liveDataList: MutableLiveData<List<DoctorList>?>
    init{
        liveDataList= MutableLiveData()
    }

    fun getLiveDataObserver(): MutableLiveData<List<DoctorList>?>{
        return liveDataList
    }

    fun makeAPICall(){
        val retroinstance = Retroinstance.getRetroInstance()
        val retroService = retroinstance.create(RetrofitServiceInterface::class.java)
        val call = retroService.getDoctorList()
        call.enqueue(object : Callback<List<DoctorList>>{
            override fun onResponse(
                call: Call<List<DoctorList>>,
                response: Response<List<DoctorList>>
            ) {
                val data = response.body()
                Log.d("Data",data.toString())
                liveDataList.postValue(response.body())
            }

            override fun onFailure(call: Call<List<DoctorList>>, t: Throwable) {
                liveDataList.postValue(null)
            }

        })
    }
}