package com.example.fakestoreapp.ui.authentication

import android.os.Bundle
import com.example.fakestoreapp.bases.BaseActivity
import com.example.imageclassification.R
import com.example.imageclassification.databinding.ActivityAuthenticationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthenticationActivity : BaseActivity() {
    private val binding: ActivityAuthenticationBinding by binding(R.layout.activity_authentication)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.executePendingBindings()
    }
}