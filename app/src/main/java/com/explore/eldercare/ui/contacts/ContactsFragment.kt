package com.explore.eldercare.ui.contacts

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.explore.eldercare.R
import com.explore.eldercare.databinding.FragmentContactsBinding
import com.explore.eldercare.databinding.FragmentDashboardBinding
import com.explore.eldercare.ui.contacts.retrofit.DoctorList
import com.explore.eldercare.ui.dashboard.DashboardViewModel

class ContactsFragment : Fragment() {

    lateinit var recyclerAdapter: DoctorListAdapter

    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val contactsViewModel =
            ViewModelProvider(this).get(ContactsViewModel::class.java)

        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initRecyclerView()

        return root
    }

    private fun initRecyclerView(){
         binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        recyclerAdapter= DoctorListAdapter(this)
        binding.recycler.adapter =recyclerAdapter
        initViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun initViewModel(){
        val viewModel= ViewModelProvider(this).get(ContactsViewModel::class.java)
        viewModel.getLiveDataObserver().observe(viewLifecycleOwner,{
            if(it !=null){
                recyclerAdapter.setDoctorList(it)
                recyclerAdapter.notifyDataSetChanged()
            }else{
                Toast.makeText(requireContext(),"Error in getting list", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.makeAPICall()
    }
}