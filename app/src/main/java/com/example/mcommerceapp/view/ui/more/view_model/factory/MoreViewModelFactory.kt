package com.example.mcommerceapp.view.ui.more.view_model.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.model.shopify_repository.currency.interfaces.ICurrencyRepo
import com.example.mcommerceapp.model.shopify_repository.user.UserRepo
import com.example.mcommerceapp.view.ui.more.view_model.MoreViewModel

class MoreViewModelFactory(
    private var userRepo: UserRepo,
    private val currencyRepo: ICurrencyRepo
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MoreViewModel::class.java)) {
            MoreViewModel(userRepo, currencyRepo) as T
        } else {
            throw IllegalArgumentException("Error")
        }
    }
}