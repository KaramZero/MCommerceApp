package com.example.mcommerceapp.view.ui.category

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.databinding.FragmentCategoryTypeBinding

import com.example.mcommerceapp.model.Keys
import com.example.mcommerceapp.model.remote_source.RemoteSource
import com.example.mcommerceapp.model.shopify_repository.product.ProductRepo
import com.example.mcommerceapp.view.ui.category.adapter.CategoryAdapter
import com.example.mcommerceapp.view.ui.category.viewmodel.CategoryViewModel
import com.example.mcommerceapp.view.ui.category.viewmodel.CategoryViewModelFactory
import com.example.mcommerceapp.view.ui.feature_product.CategorizedProductActivity
import com.example.mcommerceapp.view.ui.home.adapter.OnClickListner
import com.example.mcommerceapp.view.ui.home.viewmodel.HomeViewModel
import com.example.mcommerceapp.view.ui.home.viewmodel.HomeViewModelFactory

class CategoryTypeFragment ():OnClickListner,Fragment() {

    private var tabTitle :String = ""
    private var value :String = ""
    private var type :String = ""
    private var subCollection :String =""

    private lateinit var binding: FragmentCategoryTypeBinding
    private lateinit var homeVM: HomeViewModel
    private lateinit var homeVMFactory: HomeViewModelFactory
    private lateinit var categoryVM: CategoryViewModel
    private lateinit var categoryVMFactory: CategoryViewModelFactory
    private lateinit var categoryAdapter: CategoryAdapter

   constructor(tabTitle:String,value :String,type:String) : this() {
       this.tabTitle = tabTitle
       this.value = value
       this.type = type
   }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryTypeBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        homeVM.getProduct(Keys.PRODUCT_TYPE)
        when(type){
            Keys.VENDOR -> observeVendor()
            Keys.COLLECTION -> observeCollection()
        }
    }

    private fun observeCollection(){
        homeVM.collections.observe(viewLifecycleOwner){

            for (key in it)
            {
                if (tabTitle == key) {
                    subCollection = key
                }
            }
            categoryVM.getCategoryForCollection(Keys.PRODUCT_TYPE,subCollection)
            observerCategory()
        }
    }
    private fun observeVendor(){
        homeVM.collections.observe(viewLifecycleOwner){

            for (index in it)
            {
                if (tabTitle == index) {
                    subCollection = index
                    Log.e("index", index)
                }
            }
            Log.e("id",subCollection.toString())
            Log.e("vendor",value)

            categoryVM.getCategoryForVendor(Keys.PRODUCT_TYPE,subCollection,value)
            observerCategory()
        }
    }
    private fun observerCategory(){
        categoryVM.category.observe(viewLifecycleOwner){
           binding.progressBar.visibility = ProgressBar.INVISIBLE
            categoryAdapter.setData(it)
            Log.e("category ",it.toString())

            binding.recyclerListCategory.adapter = categoryAdapter
        }
    }
    private fun init(){
        homeVMFactory = HomeViewModelFactory(ProductRepo.getInstance(RemoteSource()))
        homeVM = ViewModelProvider(this, homeVMFactory)[HomeViewModel::class.java]
        categoryVMFactory = CategoryViewModelFactory(ProductRepo.getInstance(RemoteSource()))
        categoryVM = ViewModelProvider(this, categoryVMFactory)[CategoryViewModel::class.java]
        categoryAdapter = CategoryAdapter(this)
    }

    override fun onClick(value: String?, type: String) {
        val bundle = Bundle()
        bundle.putString("VALUE", value)
        bundle.putString("TYPE", tabTitle)
        val intent = Intent(requireContext(), CategorizedProductActivity::class.java)
        intent.putExtra("PRODUCTS", bundle)
        startActivity(intent)
    }

}