package com.example.fakestoreapp.ui.home.adapters

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestoreapp.data.models.Product
import com.example.imageclassification.R
import com.example.imageclassification.databinding.HorizontalProductRecyclerItemBinding

class HorizontalProductRecyclerAdapter (
    private val resources: Resources,
    private val onItemClicked: (Product) -> Unit,
    private val onFavouriteClicked: (Product, Boolean) -> Unit)
    : RecyclerView.Adapter<HorizontalProductRecyclerAdapter.ProductRecyclerHolder>(){

    private var products: List<Product> = mutableListOf()
    private var savedProducts: MutableList<Product> = mutableListOf()

    fun setItems (items: List<Product>, savedItems: MutableList<Product>){
        products = items
        savedProducts = savedItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductRecyclerHolder {
        val inflate = LayoutInflater.from(parent.context)
        val binding = HorizontalProductRecyclerItemBinding.inflate(inflate, parent, false)
        return ProductRecyclerHolder(binding)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ProductRecyclerHolder, position: Int) {
        holder.showData(products[position], position)
        if(savedProducts.contains(products[position])) holder.binding.favouriteBtn.setImageDrawable(resources.getDrawable(R.drawable.star_yellow))
        else holder.binding.favouriteBtn.setImageDrawable(resources.getDrawable(R.drawable.star_black))
    }

    inner class ProductRecyclerHolder(
        val binding: HorizontalProductRecyclerItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun showData(product: Product, position: Int){
            binding.product = product
            binding.root.setOnClickListener { onItemClicked(product) }
            binding.favouriteBtn.setOnClickListener {
                savedProducts.add(product)
                onFavouriteClicked(product, savedProducts.contains(product))
                notifyItemChanged(position)
            }
        }
    }
}