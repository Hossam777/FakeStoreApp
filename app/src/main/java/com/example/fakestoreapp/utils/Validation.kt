package com.example.fakestoreapp.utils


fun String.isValidEmail():Boolean{
    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    return this.matches(emailPattern.toRegex())
}

fun String.isValidPassword():Boolean{
    return this.length >= 8
}