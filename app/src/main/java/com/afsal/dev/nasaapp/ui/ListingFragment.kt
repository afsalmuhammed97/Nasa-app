package com.afsal.dev.nasaapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.afsal.dev.nasaapp.R
import com.afsal.dev.nasaapp.adapters.ListingAdapter
import com.afsal.dev.nasaapp.databinding.FragmentListingBinding
import com.afsal.dev.nasaapp.models.Planetery_dataItem
import com.afsal.dev.nasaapp.viewModel.AppViewModel


class ListingFragment : Fragment() {

    private lateinit var listingAdapter: ListingAdapter
    private lateinit var viewModel: AppViewModel
    private var _binding: FragmentListingBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListingBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(AppViewModel::class.java)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         listingAdapter= ListingAdapter(){

             navigateToDetails(it)
         }

        viewModel.data.observe(viewLifecycleOwner, Observer {
            listingAdapter.differ.submitList(it.body())
        })

        setView()

    }

     private fun navigateToDetails(item:Planetery_dataItem){
              viewModel.selectedData.postValue(item)
         findNavController().navigate(R.id.action_listingFragment_to_detailsFragment)
     }

    private fun setView(){
        binding.listRv.apply {
            layoutManager=LinearLayoutManager(context)
            adapter=listingAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}