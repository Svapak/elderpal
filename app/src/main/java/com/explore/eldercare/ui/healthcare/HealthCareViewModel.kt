package com.explore.eldercare.ui.healthcare

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.explore.eldercare.ui.models.Users
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HealthCareViewModel : ViewModel() {

    private val _list = MutableLiveData<List<HealthcareData>>()
    val list: LiveData<List<HealthcareData>> get() = _list
    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> get() = _loading
    private val _dataLoaded = MutableLiveData<Boolean>()
    val dataLoaded: LiveData<Boolean> get() = _dataLoaded
    private lateinit var database: DatabaseReference

    fun getList() {
        _loading.value = true
        _dataLoaded.value= true
        viewModelScope.launch {
            database = Firebase.database.reference
            database.child("healthcare").addValueEventListener(object  : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val newList = mutableListOf<HealthcareData>()
                        for (item in snapshot.children) {
                            Log.i("healthcare", item.key + "##" + item.value)
                            FirebaseDatabase.getInstance().getReference("users").child(item.key!!)
                                .get().addOnSuccessListener { data ->
                                    if (data.exists()) {
                                        Log.i("data", data.value.toString())
                                        val newData = data.getValue(Users::class.java)
                                        Log.i("data", newData.toString())
                                        val datatoaddd = HealthcareData(
                                            name = newData?.name!!,
                                            age = newData.age!!,
                                            experience = newData.experience!!,
                                            address = newData.adress!!,
                                            image = newData.image!!,
                                            email = newData.email!!
                                        )
                                        newList.add(datatoaddd)
                                        Log.d("list-size", newList.size.toString())
                                    }

                                }
                        }
                        _list.value = newList
                        viewModelScope.launch {
                            delay(500) // Delay for 500 milliseconds
                            _loading.value = false
                        }
                        _dataLoaded.value = true //i want to update this only after 500 millis delay
                    }
                    else{
                        viewModelScope.launch {
                            delay(500) // Delay for 500 milliseconds
                            _loading.value = false
                        }
                        _dataLoaded.value = false
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.i("error", error.message)
                    viewModelScope.launch {
                        delay(500) // Delay for 500 milliseconds
                        _loading.value = false
                    }
                    _dataLoaded.value = false
                }
            })
//            FirebaseDatabase.getInstance().getReference("healthcare").get().addOnSuccessListener {
//                if (it.exists()) {
//                    val newList = mutableListOf<HealthcareData>()
//                    for (item in it.children) {
//                        Log.i("healthcare", item.key + "##" + item.value)
//                        FirebaseDatabase.getInstance().getReference("users").child(item.key!!)
//                            .get().addOnSuccessListener { data ->
//                                if (data.exists()) {
//                                    Log.i("data", data.value.toString())
//                                    val newData = data.getValue(Users::class.java)
//                                    Log.i("data", newData.toString())
//                                    val datatoaddd = HealthcareData(
//                                        name = newData?.name!!,
//                                        age = newData.age!!,
//                                        experience = newData.experience!!,
//                                        address = newData.adress!!,
//                                        image = newData.image!!,
//                                        email = newData.email!!
//                                    )
//                                    newList.add(datatoaddd)
//                                    Log.d("list-size", newList.size.toString())
//                                }
//
//                            }
//                    }
//                    _list.value = newList
//
//                }
//            }
        }
    }
}