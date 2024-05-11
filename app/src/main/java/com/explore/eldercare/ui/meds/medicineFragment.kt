package com.explore.eldercare.ui.meds

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.explore.eldercare.R
import com.explore.eldercare.databinding.FragmentMedicineBinding
import com.explore.eldercare.ui.models.medicineModel
import com.google.gson.Gson
import java.io.InputStream

class medicineFragment : Fragment() {


    private lateinit var binding : FragmentMedicineBinding

    private lateinit var recyclerView : RecyclerView
    private var dataList : ArrayList<medicineModel> = ArrayList<medicineModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMedicineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.recycler.layoutManager =LinearLayoutManager(requireContext())
        getData()

        val itemAdapter = medicineAdapter(dataList)
        binding.recycler.adapter = itemAdapter
        binding.recycler.setHasFixedSize(true)
    }

    private fun getData(){
        val inputStream : InputStream = requireContext().assets.open("medicine.json")
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