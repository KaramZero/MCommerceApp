package com.example.mcommerceapp.pojo.user

data class User(
    val displayName: String = "no name",
    val email: String = "no email",
    val onShopify: Boolean = false,
    val addresses:MutableList<Address> = mutableListOf(),
    var userID:String = ""
)
