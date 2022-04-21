package com.said.contryapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.said.contryapp.R
import com.said.contryapp.util.downloadFromUrl
import com.said.contryapp.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {
    private var countryUuid = 0
    private lateinit var viewModel : DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)

        arguments?.let {
            countryUuid = it.getInt("countryUuid")
            Log.e("countryUuid", "$countryUuid")
            viewModel.getDataFromRoom(countryUuid)
        }

        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer { data ->
            data?.let { country ->
                countryName.text = country.countryName
                countryRegion.text = country.countryRegion
                countryCapital.text = country.countryCapital
                countryCurrency.text = country.countryCurrency
                countryLanguage.text = country.countryLanguage
                imageView.downloadFromUrl(country.countryFlagUrl)
            }

        })
    }
}