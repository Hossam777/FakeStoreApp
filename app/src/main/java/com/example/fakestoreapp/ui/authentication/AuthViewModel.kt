package com.example.fakestoreapp.ui.authentication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakestoreapp.data.models.User
import com.example.fakestoreapp.usecases.LoginUseCase
import com.example.fakestoreapp.usecases.SignupUseCase
import com.example.fakestoreapp.utils.isValidEmail
import com.example.fakestoreapp.utils.isValidPassword
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val signupUseCase: SignupUseCase
): ViewModel() {
    var isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    var errMessage: MutableLiveData<String> = MutableLiveData()
    var user: MutableLiveData<User> = MutableLiveData()

    fun login(email: String, password: String){
        isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            loginUseCase.login(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    user.postValue(it)
                    isLoading.postValue(false)
                }, {
                    errMessage.postValue("Wrong Credentials!")
                    println(it.localizedMessage)
                    isLoading.postValue(false)
                })
        }
    }
    fun signup(name: String, email: String, password: String){
        isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            if(signupUseCase.signup(name, email,password)){
                user.postValue(User(name=name, email = email, password = password))
                isLoading.postValue(false)
            }else{
                errMessage.postValue("Email is already registered")
                isLoading.postValue(false)
            }
        }
    }
}