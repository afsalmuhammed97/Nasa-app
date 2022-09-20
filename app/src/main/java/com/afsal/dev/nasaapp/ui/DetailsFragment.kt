package com.afsal.dev.nasaapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.afsal.dev.nasaapp.databinding.FragmentDetailsBinding
import com.afsal.dev.nasaapp.models.PlaneteryDataItem
import com.afsal.dev.nasaapp.viewModel.AppViewModel
import com.bumptech.glide.Glide

class DetailsFragment : Fragment() {


    private lateinit var viewModel: AppViewModel
    private  var _binding: FragmentDetailsBinding? =null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding=FragmentDetailsBinding.inflate(inflater,container,false)

        viewModel=ViewModelProvider(requireActivity()).get(AppViewModel::class.java)
        return  binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.selectedData.observe(viewLifecycleOwner, Observer {
                                     setView(it)
        })
    }

    private fun setView(data:PlaneteryDataItem){
        context?.let {
            Glide
                .with(it)
                .load(data.url)
                // .centerCrop()
                .fitCenter()
                // .placeholder(R.drawable.image_place_holder)
                .into(binding.imageView2)
        }

        binding.apply {
            tittle.text=data.title
            distcription.text=data.explanation
            date.text=data.date
        }


    }




    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }


}