package com.explore.eldercare.ui.meds

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.explore.eldercare.R
import com.explore.eldercare.databinding.ActivityMedicineBinding
import com.explore.eldercare.ui.models.medicineModel
import com.google.gson.Gson
import java.io.InputStream

class medicineActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMedicineBinding
    private var dataList : ArrayList<medicineModel> = ArrayList<medicineModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMedicineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycler.layoutManager = LinearLayoutManager(this)

        getData()
        binding.recycler.setHasFixedSize(true)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }

    private fun getData(){
        val inputStream : InputStream = this.assets.open("medicine.json")
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()

        val json = String(buffer, Charsets.UTF_8)

        val gson = Gson()
        val meds = gson.fromJson(json, Array<medicineModel>::class.java)
        dataList.addAll(meds)
    }
}