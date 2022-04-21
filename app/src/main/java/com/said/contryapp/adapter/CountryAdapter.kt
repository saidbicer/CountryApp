package com.said.contryapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.said.contryapp.R
import com.said.contryapp.databinding.ItemCountryBinding
import com.said.contryapp.model.Country
import com.said.contryapp.util.downloadFromUrl
import com.said.contryapp.view.FeedFragmentDirections
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.android.synthetic.main.item_country.view.*
import java.util.logging.Logger
import kotlinx.android.synthetic.main.fragment_detail.view.imageView as imageView1

class CountryAdapter(var countryList: List<Country>) :
    RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() , CountryClickListener{

    class CountryViewHolder(var view: ItemCountryBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
//        val view = inflater.inflate(R.layout.item_country, parent, false)
        val view = DataBindingUtil.inflate<ItemCountryBinding>(
            inflater,
            R.layout.item_country,
            parent,
            false
        )
        return CountryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.view.country = countryList[position]
        holder.view.listener = this
        /*
        holder.view.tvName.text = countryList[position].countryName
        holder.view.tvRegion.text = countryList[position].countryRegion
        holder.view.imageView.downloadFromUrl(countryList[position].countryFlagUrl)


        holder.view.setOnClickListener {
            val action = FeedFragmentDirections.actionFeedFragmentToDetailFragment(countryList[position].uuid)
            it.findNavController().navigate(action)
        }
        */
    }

    fun updateCountryList(newCountryList: List<Country>) {
        countryList = newCountryList
        notifyDataSetChanged()
    }

    override fun onCountryClicked(v: View) {
        val uuid = v.tvUuid.text.toString().toInt()
        val action = FeedFragmentDirections.actionFeedFragmentToDetailFragment(uuid)
        v.findNavController().navigate(action)
    }
}