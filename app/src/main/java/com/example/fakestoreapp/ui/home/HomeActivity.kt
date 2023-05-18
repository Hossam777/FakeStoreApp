package com.example.fakestoreapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fakestoreapp.bases.BaseActivity
import com.example.fakestoreapp.data.models.Product
import com.example.fakestoreapp.ui.home.adapters.CategoryRecyclerAdapter
import com.example.fakestoreapp.ui.home.adapters.ProductRecyclerAdapter
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
    private lateinit var horizontalProductsAdapter: ProductRecyclerAdapter
    private lateinit var verticalProductsAdapter: ProductRecyclerAdapter
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

        homeViewModel.getSavedProducts()
        homeViewModel.getAllCategories()
        homeViewModel.getProducts()

        //initializing adapters
        horizontalProductsAdapter = ProductRecyclerAdapter(false, resources, {
            showProductDialog(productsDialog, productsBindingDialog, it)
        }, { product, fav ->
            handleSavingProducts(product, fav)
        })
        verticalProductsAdapter = ProductRecyclerAdapter(true, resources, {
            showProductDialog(productsDialog, productsBindingDialog, it)
        }, { product, fav ->
            handleSavingProducts(product, fav)
        })
        categoriesAdapter = CategoryRecyclerAdapter(resources) {
            categoriesDialog.dismiss()
            var products = mutableListOf<Product>()
            homeViewModel.products.value?.forEach { product ->
                if(product.category == it) products.add(product)
            }
            if(it == "ALL")
                products = homeViewModel.products.value?.toMutableList()!!
            horizontalProductsAdapter.setItems(products, homeViewModel.savedProducts.value?.toMutableList()!!)
            verticalProductsAdapter.setItems(products, homeViewModel.savedProducts.value?.toMutableList()!!)
        }
        binding.horizontalProductsRecycler.adapter = horizontalProductsAdapter
        binding.horizontalProductsRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.verticalProductsRecycler.adapter = verticalProductsAdapter
        binding.verticalProductsRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        categoriesBindingDialog.dialogRecycler.adapter = categoriesAdapter
        categoriesBindingDialog.dialogRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //initializing viewmodel observers
        homeViewModel.dataSetUp.observe(this){
            if(it == 2){
                homeViewModel.products.value?.let { it1 ->
                    homeViewModel.savedProducts.value?.let { it2 ->
                        horizontalProductsAdapter.setItems(it1, it2.toMutableList())
                        verticalProductsAdapter.setItems(it1, it2.toMutableList())
                    }
                }
            }
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
        homeViewModel.savedProducts.observe(this){
            println(it.toString())
        }
    }
    private fun handleSavingProducts(product: Product, favourite: Boolean){
        if(favourite) homeViewModel.addProductToSavedProducts(product)
        else homeViewModel.deleteProductFromSavedProducts(product)
    }
    private fun showProductDialog(dialog: AlertDialog, productDialogBinding: ProductDialogBinding, product: Product){
        productDialogBinding.product = product
        dialog.show()
    }
}