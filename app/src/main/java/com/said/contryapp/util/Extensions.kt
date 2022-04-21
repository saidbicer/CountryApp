package com.said.contryapp.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.said.contryapp.R

//fun String.myExtension(myPArameter : String){
//
//}

fun ImageView.downloadFromUrl(url : String?){
    url?.let {
        Glide
            .with(context)
            .load(url)
            .centerCrop()
            .placeholder(R.mipmap.ic_launcher)
            .into(this);
    }

}