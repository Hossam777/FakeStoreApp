package com.example.fakestoreapp.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fakestoreapp.bases.BaseActivity
import com.example.imageclassification.R
import com.example.imageclassification.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity() {
    private val binding: ActivityHomeBinding by binding(R.layout.activity_home)
    @Inject lateinit var homeViewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.executePendingBindings()
    }
}