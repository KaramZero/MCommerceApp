package com.example.mcommerceapp.view.ui.category.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcommerceapp.model.shopify_repository.currency.interfaces.StoredCurrency
import com.example.mcommerceapp.model.shopify_repository.product.ICategoryRepo
import com.example.mcommerceapp.model.shopify_repository.product.ProductRepo
import com.example.mcommerceapp.model.shopify_repository.user.UserRepo
import com.example.mcommerceapp.pojo.customcollections.CustomCollections
import com.example.mcommerceapp.pojo.products.ProductFields
import com.example.mcommerceapp.pojo.products.Products
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoryViewModel(
    var iCategory: ICategoryRepo,
    var iCurrency: StoredCurrency,
    private val iUser: UserRepo
) : ViewModel() {


    private val _subCategory: MutableLiveData<HashSet<ProductFields>> = ProductRepo.subCollections
    var subCategory: LiveData<HashSet<ProductFields>> = _subCategory

    private val _customCollection = MutableLiveData<ArrayList<CustomCollections>>()
    var customCollection: LiveData<ArrayList<CustomCollections>> = _customCollection

    private val _collectionProducts = MutableLiveData<ArrayList<Products>>()
    var collectionProducts: LiveData<ArrayList<Products>> = _collectionProducts

    private val _allProducts: MutableLiveData<ArrayList<Products>> = ProductRepo.allProducts
    var allProducts: LiveData<ArrayList<Products>> = _allProducts

    val currencySymbol = iCurrency.getCurrencySymbol()
    val currencyValue = iCurrency.getCurrencyValue()

    fun getCollectionId(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val customCollection = iCategory.getCollectionId(id)
            withContext(Dispatchers.Main) {
                _customCollection.postValue(customCollection)
            }
        }
    }

    fun getCollectionProducts(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val collectionProducts = iCategory.getProductsCollection(id)
            withContext(Dispatchers.Main) {
                _collectionProducts.postValue(collectionProducts)
            }
        }
    }

    fun isLogged(): Boolean {
        return iUser.getLoggedInState()
    }
}