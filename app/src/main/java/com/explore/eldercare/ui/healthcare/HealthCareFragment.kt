package com.explore.eldercare.ui.healthcare

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.explore.eldercare.R
import com.explore.eldercare.databinding.FragmentHealthCareBinding
import com.explore.eldercare.ui.notifications.NotificationsViewModel
import com.explore.eldercare.ui.notifications.RemindersAdapter

class HealthCareFragment : Fragment() {

    private lateinit var adapter: HealthCareAdapter
    private lateinit var viewModel: HealthCareViewModel
    private var _binding: FragmentHealthCareBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHealthCareBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity(),
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application))[HealthCareViewModel::class.java]
        binding.rvHealthcare.layoutManager = LinearLayoutManager(context)
        binding.rvHealthcare.setHasFixedSize(true)
        adapter = HealthCareAdapter(emptyList())
        binding.rvHealthcare.adapter = adapter

        viewModel.loading.observe(viewLifecycleOwner) { showLoading ->
            if (showLoading) {
                Toast.makeText(context,"Loading....",Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context,"Done",Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.list.observe(viewLifecycleOwner){
            adapter.setData(it)
        }

        viewModel.getList()
    }
}