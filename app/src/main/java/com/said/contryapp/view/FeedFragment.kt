package com.said.contryapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.said.contryapp.R
import com.said.contryapp.adapter.CountryAdapter
import com.said.contryapp.viewmodel.FeedViewModel
import kotlinx.android.synthetic.main.fragment_feed.*

class FeedFragment : Fragment() {

    private lateinit var viewModel: FeedViewModel
    private var countryAdapter = CountryAdapter(arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        btnAction.setOnClickListener {
//            val action = FeedFragmentDirections.actionFeedFragmentToDetailFragment(30)
//            it.findNavController().navigate(action)
//        }

        viewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)
        viewModel.refreshData()
        observeLiveData()

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = countryAdapter

        swipeRefreshLayout.setOnRefreshListener {
            recyclerView.visibility = View.GONE
            tvError.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            swipeRefreshLayout.isRefreshing = false
            viewModel.getDataFromAPI()
        }

    }

    private fun observeLiveData() {
        viewModel.countries.observe(viewLifecycleOwner, Observer { countries ->
            countries?.let {
                recyclerView.visibility = View.VISIBLE
                countryAdapter.updateCountryList(it)
                tvError.visibility = View.GONE
                progressBar.visibility = View.GONE
            }
        })

        viewModel.countryError.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if (it) {
                    tvError.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    recyclerView.visibility = View.GONE

                } else {
                    tvError.visibility = View.GONE
                }
            }
        })

        viewModel.countryLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (it) {
                    progressBar.visibility = View.VISIBLE
                    tvError.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                } else {
                    progressBar.visibility = View.GONE
                }
            }
        })
    }

}