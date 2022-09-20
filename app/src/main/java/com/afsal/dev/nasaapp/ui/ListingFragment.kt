package com.afsal.dev.nasaapp.ui

import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.afsal.dev.nasaapp.R
import com.afsal.dev.nasaapp.adapters.ListingAdapter
import com.afsal.dev.nasaapp.databinding.FragmentListingBinding
import com.afsal.dev.nasaapp.helper.Resource
import com.afsal.dev.nasaapp.models.PlaneteryDataItem
import com.afsal.dev.nasaapp.viewModel.AppViewModel
import com.google.android.material.snackbar.Snackbar


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

        viewModel.planeteryData.observe(viewLifecycleOwner) {result->

            Log.d("MMM","result data ${result.data}")
                    listingAdapter.differ.submitList(result.data )

            binding.progressBar.isVisible =result is Resource.Loading && result.data.isNullOrEmpty()
       //+    val errorVisibility =result is Resource.Error && result.data.isNullOrEmpty()
            val errorMessge= result.error.toString()

             Log.d("MMM","result error ${errorMessge.toString()}")

                   showSnackBar(errorMessge.toString(),)

        }

        setView()

    }


      private  fun showSnackBar(message:String,action:(()->Unit)?=null){

            val snackbar=Snackbar.make(binding.listRv,message, Snackbar.LENGTH_LONG)

            action?.let {
                snackbar.setAction("Retry"){
                    it()
                }
            }

            snackbar.show()
        }



     private fun navigateToDetails(item:PlaneteryDataItem){
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