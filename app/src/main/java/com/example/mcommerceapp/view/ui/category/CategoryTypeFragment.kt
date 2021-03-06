package com.example.mcommerceapp.view.ui.category

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.example.mcommerceapp.R
import com.example.mcommerceapp.databinding.FragmentCategoryTypeBinding
import com.example.mcommerceapp.model.shopify_repository.currency.CurrencyRepo
import com.example.mcommerceapp.model.remote_source.products.ProductRemoteSource
import com.example.mcommerceapp.model.shopify_repository.product.ProductRepo
import com.example.mcommerceapp.model.shopify_repository.user.UserRepo
import com.example.mcommerceapp.pojo.products.Products
import com.example.mcommerceapp.view.ui.category.adapter.CategoryAdapter
import com.example.mcommerceapp.view.ui.category.adapter.OnClickListener
import com.example.mcommerceapp.view.ui.category.viewmodel.CategoryViewModel
import com.example.mcommerceapp.view.ui.category.viewmodel.CategoryViewModelFactory
import com.example.mcommerceapp.view.ui.product_detail.view.ProductDetail
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.slider.RangeSlider


class CategoryTypeFragment() : OnClickListener, Fragment() {

    private var tabTitle: String = ""
    private var minValue = 10.0
    private var maxValue = 2000.0
    private lateinit var products: ArrayList<Products>
    private var checkboxText: ArrayList<String> = arrayListOf()


    private lateinit var binding: FragmentCategoryTypeBinding
    private lateinit var categoryVM: CategoryViewModel
    private lateinit var categoryVMFactory: CategoryViewModelFactory
    private lateinit var categoryAdapter: CategoryAdapter

    private lateinit var checkboxT_Shirt: CheckBox
    private lateinit var checkboxAccessories: CheckBox
    private lateinit var checkboxShoes: CheckBox

    constructor(tabTitle: String) : this() {
        this.tabTitle = tabTitle
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.filterImageView.setOnClickListener {
            showSupportBottomSheet()
        }
    }

    override fun onResume() {
        super.onResume()
        when (tabTitle) {
            "ALL"-> observerAllProducts()
            else -> {
                categoryVM.getCollectionId(tabTitle)
                observerCollectionId()
            }
        }

    }

    private fun observerCollectionId() {
        categoryVM.customCollection.observe(viewLifecycleOwner) {
            categoryVM.getCollectionProducts(it[0].id.toString())
            observerCollectionProducts()
        }
    }

    private fun observerCollectionProducts() {
        categoryVM.collectionProducts.removeObservers(viewLifecycleOwner)
        categoryVM.collectionProducts.observe(viewLifecycleOwner) {
            products = it
            binding.progressBar.visibility = ProgressBar.INVISIBLE
            categoryAdapter.setData(it, categoryVM.currencySymbol, categoryVM.currencyValue)
            binding.recyclerProduct.adapter = categoryAdapter
            TransitionManager.beginDelayedTransition(binding.recyclerProduct, Slide())


        }
    }

    private fun observerAllProducts() {
        categoryVM.allProducts.removeObservers(viewLifecycleOwner)
        categoryVM.allProducts.observe(viewLifecycleOwner) {
            products = it
            binding.progressBar.visibility = ProgressBar.INVISIBLE
            categoryAdapter.setData(it, categoryVM.currencySymbol, categoryVM.currencyValue)
            binding.recyclerProduct.adapter = categoryAdapter
            TransitionManager.beginDelayedTransition(binding.recyclerProduct, Slide())

        }
    }


    private fun init() {
        categoryVMFactory = CategoryViewModelFactory(
            ProductRepo.getInstance(ProductRemoteSource.getInstance()), CurrencyRepo.getInstance(
                ProductRemoteSource.getInstance(), requireContext
                    ()
            ),
            UserRepo.getInstance(requireContext())
        )
        categoryVM = ViewModelProvider(this, categoryVMFactory)[CategoryViewModel::class.java]
        categoryAdapter = CategoryAdapter(requireContext(), this)
    }

    override fun onClick(value: String) {
        val intent = Intent(requireContext(), ProductDetail::class.java)
        intent.putExtra("PRODUCTS_ID", value)
        startActivity(intent)

    }

    @SuppressLint("InflateParams")
    fun showSupportBottomSheet() {
        val dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.buttom_sheet_filter, null)

        val seekBar = view.findViewById<RangeSlider>(R.id.seekBar)
        checkboxT_Shirt = view.findViewById(R.id.t_shirt_checkbox)
        checkboxAccessories = view.findViewById(R.id.accessories_checkbox)
        checkboxShoes = view.findViewById(R.id.shoes_checkbox)
        val btnSubmit = view.findViewById<Button>(R.id.submitBtn)


        categoryVM.subCategory.observe(viewLifecycleOwner) {
            it.forEach {
                when(it.productType){
                    "T-SHIRTS" -> checkboxT_Shirt.text = it.productType
                    "ACCESSORIES" -> checkboxAccessories.text = it.productType
                    "SHOES" ->  checkboxShoes.text = it.productType
                }
            }
        }

        btnSubmit.setOnClickListener {
            checkboxText.clear()
            if (checkboxT_Shirt.isChecked) {
                checkboxText.add(checkboxT_Shirt.text.toString())
            }
            if (checkboxAccessories.isChecked) {
                checkboxText.add(checkboxAccessories.text.toString())
            }
            if (checkboxShoes.isChecked) {
                checkboxText.add(checkboxShoes.text.toString())
            }

            filterProducts()
            dialog.dismiss()
        }

        seekBar.setValues(10.0f, 2000.0f)
        seekBar.stepSize = 5f

        seekBar.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener {
            @SuppressLint("RestrictedApi")
            override fun onStartTrackingTouch(slider: RangeSlider) {

            }

            @SuppressLint("RestrictedApi")
            override fun onStopTrackingTouch(slider: RangeSlider) {
                val values = slider.values
                this@CategoryTypeFragment.minValue = String.format("%.2f", values[0]).toDouble()
                this@CategoryTypeFragment.maxValue = String.format("%.2f", values[1]).toDouble()
            }
        })

        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()
    }

    private fun filterProducts() {
        val filterProducts: ArrayList<Products> = arrayListOf()

        if (checkboxText.size == 0) {
            checkboxText.add(checkboxT_Shirt.text.toString())
            checkboxText.add(checkboxAccessories.text.toString())
            checkboxText.add(checkboxShoes.text.toString())
        }

        Log.e("data",products.size.toString())
        for (index in 0 until this.products.size) {
            if ((products[index].variants[0].price?.toDouble()!! >= this.minValue)
                && (this.maxValue >= this.products[index].variants[0].price!!.toDouble())
                && (checkboxText.contains(products[index].productType))

            ) {
                filterProducts.add(products[index])
            }
        }
        if (filterProducts.size > 0) {
            binding.foundTxt.visibility = View.INVISIBLE
            binding.recyclerProduct.visibility = View.VISIBLE
            categoryAdapter.setData(
                filterProducts,
                categoryVM.currencySymbol,
                categoryVM.currencyValue
            )
        }  else {
            binding.foundTxt.visibility = View.VISIBLE
            binding.recyclerProduct.visibility = View.INVISIBLE
            this@CategoryTypeFragment.minValue =10.0
            this@CategoryTypeFragment.maxValue = 2000.0
        }
    }
}