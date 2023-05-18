package com.example.fakestoreapp.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fakestoreapp.bases.BaseActivity
import com.example.fakestoreapp.data.models.Product
import com.example.fakestoreapp.ui.home.adapters.CategoryRecyclerAdapter
import com.example.fakestoreapp.ui.home.adapters.HorizontalProductRecyclerAdapter
import com.example.fakestoreapp.ui.home.adapters.VerticalProductRecyclerAdapter
import com.example.imageclassification.R
import com.example.imageclassification.databinding.ActivityHomeBinding
import com.example.imageclassification.databinding.CategoriesDialogBinding
import com.example.imageclassification.databinding.ProductDialogBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity() {
    private val binding: ActivityHomeBinding by binding(R.layout.activity_home)
    @Inject lateinit var homeViewModel: HomeViewModel
    private lateinit var horizontalProductsAdapter: HorizontalProductRecyclerAdapter
    private lateinit var verticalProductsAdapter: VerticalProductRecyclerAdapter
    private lateinit var categoriesAdapter: CategoryRecyclerAdapter
    private lateinit var productsDialog: MaterialAlertDialogBuilder
    private lateinit var categoriesDialog: MaterialAlertDialogBuilder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.executePendingBindings()
        val inflater = LayoutInflater.from(this)
        //intializing categories dialog
        categoriesDialog = MaterialAlertDialogBuilder(this)
        val categoriesBindingDialog = CategoriesDialogBinding.inflate(inflater)
        categoriesDialog.setView(categoriesBindingDialog.root)
        val categoriesDialog = categoriesDialog.show()
        categoriesDialog.dismiss()

        //intializing products dialog
        productsDialog = MaterialAlertDialogBuilder(this)
        val productsBindingDialog = ProductDialogBinding.inflate(inflater)
        productsDialog.setView(productsBindingDialog.root)
        val productsDialog = productsDialog.show()
        productsDialog.dismiss()

        homeViewModel.getAllCategories()
        homeViewModel.getProducts()

        //initializing adapters
        horizontalProductsAdapter = HorizontalProductRecyclerAdapter {
            showProductDialog(productsDialog, productsBindingDialog, it)
        }
        verticalProductsAdapter = VerticalProductRecyclerAdapter {
            showProductDialog(productsDialog, productsBindingDialog, it)
        }
        categoriesAdapter = CategoryRecyclerAdapter(resources) {
            categoriesDialog.dismiss()
            var products = mutableListOf<Product>()
            homeViewModel.products.value?.forEach { product ->
                if(product.category == it) products.add(product)
            }
            if(it == "ALL")
                products = homeViewModel.products.value?.toMutableList()!!
            horizontalProductsAdapter.setItems(products)
            verticalProductsAdapter.setItems(products)
        }
        binding.horizontalProductsRecycler.adapter = horizontalProductsAdapter
        binding.horizontalProductsRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.verticalProductsRecycler.adapter = verticalProductsAdapter
        binding.verticalProductsRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        categoriesBindingDialog.dialogRecycler.adapter = categoriesAdapter
        categoriesBindingDialog.dialogRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //initializing viewmodel observers
        homeViewModel.products.observe(this){
            horizontalProductsAdapter.setItems(it)
            verticalProductsAdapter.setItems(it)
        }
        homeViewModel.categories.observe(this){
            val categories = mutableListOf("ALL")
            it.forEach { categories.add(it) }
            categoriesAdapter.setItems(categories)
        }
        //view listeners
        binding.filterBtn.setOnClickListener {
            categoriesDialog.show()
        }
        homeViewModel.isLoading.observe(this){
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
    }
    private fun showProductDialog(dialog: AlertDialog, productDialogBinding: ProductDialogBinding, product: Product){
        productDialogBinding.product = product
        dialog.show()
    }
}