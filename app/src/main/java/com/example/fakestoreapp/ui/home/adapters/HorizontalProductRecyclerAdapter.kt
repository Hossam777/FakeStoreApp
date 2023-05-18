package com.example.fakestoreapp.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestoreapp.data.models.Product
import com.example.imageclassification.databinding.HorizontalProductRecyclerItemBinding

class HorizontalProductRecyclerAdapter (private val onItemClicked: (Product) -> Unit)
    : RecyclerView.Adapter<HorizontalProductRecyclerAdapter.ProductRecyclerHolder>(){

    private var products: List<Product> = mutableListOf()

    fun setItems (items: List<Product>){
        products = items
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
        holder.showData(products[position])
    }

    inner class ProductRecyclerHolder(
        val binding: HorizontalProductRecyclerItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun showData(product: Product){
            binding.product = product
            binding.root.setOnClickListener { onItemClicked(product) }
        }
    }
}