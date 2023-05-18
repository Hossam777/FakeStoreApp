package com.example.fakestoreapp.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("image")
fun loadImage(view: ImageView, url:String?) {
    url?.let {  Glide.with(view.context).load(it).into(view)  }
}

@BindingAdapter("price")
fun loadPrice(view: TextView, price:Double?) {
    price?.let { view.text = "$price EGP" }
}

@BindingAdapter("rating")
fun loadRating(view: TextView, rate:Double?) {
    rate?.let { view.text = "$rate" }
}

@BindingAdapter("rating")
fun loadRating(view: TextView, rate:Int?) {
    rate?.let { view.text = "$rate" }
}