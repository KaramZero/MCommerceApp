package com.example.mcommerceapp.view.ui.feature_product.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcommerceapp.model.shopify_repository.currency.interfaces.StoredCurrency
import com.example.mcommerceapp.model.shopify_repository.product.ICategoryRepo
import com.example.mcommerceapp.model.shopify_repository.product.ProductRepo
import com.example.mcommerceapp.model.shopify_repository.user.UserRepo
import com.example.mcommerceapp.pojo.products.ProductFields
import com.example.mcommerceapp.pojo.products.Products
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategorizedProductVM(
    private var iCategory: ICategoryRepo,
    private var iCurrency: StoredCurrency,
    private val iUser: UserRepo
) : ViewModel() {

    private val _subCategory: MutableLiveData<HashSet<ProductFields>> = ProductRepo.subCollections
    var subCategory: LiveData<HashSet<ProductFields>> = _subCategory

    private val _products = MutableLiveData<ArrayList<Products>>()
    var products: LiveData<ArrayList<Products>> = _products


    private val _allProducts: MutableLiveData<ArrayList<Products>> = ProductRepo.allProducts
    var allProducts: LiveData<ArrayList<Products>> = _allProducts


    val currencySymbol = iCurrency.getCurrencySymbol()
    val currencyValue = iCurrency.getCurrencyValue()

    val isLogged = iUser.getLoggedInState()

    fun getProductsVendor(vendor: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val product = iCategory.getProductsVendor(vendor)
            withContext(Dispatchers.Main) {
                _products.postValue(product)
            }
        }
    }

}