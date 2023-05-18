package com.example.fakestoreapp.ui.home.adapters

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imageclassification.R
import com.example.imageclassification.databinding.CategoryRecyclerItemBinding

class CategoryRecyclerAdapter (private val resources: Resources, private val onItemClicked: (String) -> Unit)
    : RecyclerView.Adapter<CategoryRecyclerAdapter.CategoryRecyclerHolder>(){
    var selectedCategory = ""
    private var categories: List<String> = mutableListOf()

    fun setItems (items: List<String>){
        categories = items
        selectedCategory = items[0]
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryRecyclerHolder {
        val inflate = LayoutInflater.from(parent.context)
        val binding = CategoryRecyclerItemBinding.inflate(inflate, parent, false)
        return CategoryRecyclerHolder(binding)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoryRecyclerHolder, position: Int) {
        holder.showData(categories[position])
        if(selectedCategory == categories[position])
            holder.binding.root.background = resources.getDrawable(R.drawable.rect_with_10_radius_colored)
        else
            holder.binding.root.background = resources.getDrawable(R.drawable.rect_with_10_radius)
    }

    inner class CategoryRecyclerHolder(
        val binding: CategoryRecyclerItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun showData(category: String){
            binding.category = category
            binding.root.setOnClickListener {
                onItemClicked(category)
                notifyDataSetChanged()
                selectedCategory = category
            }
        }
    }
}