package com.example.fakestoreapp.ui.home.adapters

import android.content.res.Resources
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestoreapp.data.models.Product
import com.example.imageclassification.R
import com.example.imageclassification.databinding.HorizontalProductRecyclerItemBinding
import com.example.imageclassification.databinding.VerticalProductRecyclerItemBinding

class ProductRecyclerAdapter (
    private val vertical: Boolean,
    private val resources: Resources,
    private val onItemClicked: (Product) -> Unit,
    private val onFavouriteClicked: (Product, Boolean) -> Unit)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var products: List<Product> = mutableListOf()
    private var savedProducts: MutableList<Product> = mutableListOf()

    fun setItems (items: List<Product>, savedItems: MutableList<Product>){
        products = items
        savedProducts = savedItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        if(vertical) return VerticalProductRecyclerHolder(VerticalProductRecyclerItemBinding.inflate(inflate, parent, false))
        return HorizontalProductRecyclerHolder(HorizontalProductRecyclerItemBinding.inflate(inflate, parent, false))
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(vertical){
            (holder as VerticalProductRecyclerHolder).showData(products[position], position)
            if(savedProduct(products[position])) (holder).binding.favouriteBtn.setImageDrawable(resources.getDrawable(R.drawable.star_yellow))
            else (holder).binding.favouriteBtn.setImageDrawable(resources.getDrawable(R.drawable.star_black))
        }else{
            (holder as HorizontalProductRecyclerHolder).showData(products[position], position)
            if(savedProduct(products[position])) (holder).binding.favouriteBtn.setImageDrawable(resources.getDrawable(R.drawable.star_yellow))
            else (holder).binding.favouriteBtn.setImageDrawable(resources.getDrawable(R.drawable.star_black))
        }
    }

    fun savedProduct(product: Product): Boolean {
        savedProducts.forEach { if(it.id == product.id) return true }
        return false
    }

    fun removeProduct(product: Product) {
        for (p in savedProducts){
            if(p.id == product.id) {
                savedProducts.remove(p)
                return
            }
        }
    }

    inner class VerticalProductRecyclerHolder(
        val binding: VerticalProductRecyclerItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun showData(product: Product, position: Int){
            binding.product = product
            binding.root.setOnClickListener { onItemClicked(product) }
            binding.favouriteBtn.setOnClickListener {
                if(savedProduct(product)) removeProduct(product)
                else savedProducts.add(product)
                onFavouriteClicked(product, savedProduct(product))
                notifyItemChanged(position)
            }
        }
    }
    inner class HorizontalProductRecyclerHolder(
        val binding: HorizontalProductRecyclerItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun showData(product: Product, position: Int){
            binding.product = product
            binding.root.setOnClickListener { onItemClicked(product) }
            binding.favouriteBtn.setOnClickListener {
                if(savedProduct(product)) removeProduct(product)
                else savedProducts.add(product)
                onFavouriteClicked(product, savedProduct(product))
                notifyItemChanged(position)
            }
        }
    }
}