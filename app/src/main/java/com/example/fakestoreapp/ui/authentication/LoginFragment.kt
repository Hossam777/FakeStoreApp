package com.example.fakestoreapp.ui.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.fakestoreapp.utils.NetworkManager
import com.example.fakestoreapp.utils.hideKeyboard
import com.example.fakestoreapp.utils.isValidEmail
import com.example.fakestoreapp.utils.isValidPassword
import com.example.imageclassification.R
import com.example.imageclassification.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {


    @Inject lateinit var authViewModel: AuthViewModel
    lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.loginBtn.setOnClickListener {
            if(authViewModel.isLoading.value == true) return@setOnClickListener
            activity?.currentFocus?.hideKeyboard()
            val email = binding.emailET.text.toString()
            val password = binding.passwordET.text.toString()
            if(validateData(email, password)){
                authViewModel.login(email,password)
            }
        }
        binding.signupBtn.setOnClickListener {
            findNavController().navigate(R.id.navigateToSignupScreen)
        }
        authViewModel.errMessage.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        authViewModel.user.observe(viewLifecycleOwner){
            //navigate to home
            Toast.makeText(requireContext(), "Loggedin", Toast.LENGTH_SHORT).show()
        }
        authViewModel.isLoading.observe(viewLifecycleOwner){
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
        setWatchers()
        return binding.root
    }

    private fun setWatchers(){
        binding.emailET.addTextChangedListener {
            binding.emailTL.isErrorEnabled = false
        }
        binding.passwordET.addTextChangedListener {
            binding.passwordTL.isErrorEnabled = false
        }
    }

    fun validateData(email: String, password: String): Boolean {
        var valid = true
        when{
            !email.isValidEmail() -> {
                binding.emailTL.error = "Email is not valid."
                valid = false
            }
            !password.isValidPassword() -> {
                binding.passwordTL.error = "password must be at least 8 characters."
                valid = false
            }
        }
        if(valid)
            return true
        return false
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) = LoginFragment().apply {}
    }
}