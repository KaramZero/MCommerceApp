package com.example.mcommerceapp.view.ui.payment.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcommerceapp.model.shopify_repository.addresses.AddressesRepo
import com.example.mcommerceapp.model.shopify_repository.orders.IOrderPayment
import com.example.mcommerceapp.model.shopify_repository.user.UserRepo
import com.example.mcommerceapp.pojo.customers.Addresses
import com.example.mcommerceapp.pojo.orders.DiscountCodes
import com.example.mcommerceapp.pojo.orders.ShippingAddress
import com.example.mcommerceapp.pojo.user.User
import draft_orders.DraftOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import orders.LineItems
import orders.Order

class PaymentViewmodel(
    private val iOrder: IOrderPayment,
    private val addressesRepo: AddressesRepo,
    private val userRepo: UserRepo
) : ViewModel() {
    private val _addresses = MutableLiveData<Addresses>()
    val addresses: LiveData<Addresses> = _addresses

    fun getUser(): LiveData<User> {
        return userRepo.retrieveUserFromFireStore()
    }

    fun getAddress(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            val addresses = addressesRepo.getAddressByCustomerID(user.userID)
            withContext(Dispatchers.Main) {
                addresses.forEach {
                    if (it.default!!) {
                        _addresses.postValue(it)
                    }
                }
            }
        }
    }

    fun createOrder(
        draftOrders: ArrayList<DraftOrder>,
        shippingAddress: ShippingAddress,
        user: User,
        discountCodes: ArrayList<DiscountCodes>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val order = makeOrder(draftOrders, shippingAddress, user, discountCodes)
            iOrder.createOrder(order)
        }
    }


    private fun adjustInventoryItem(variantId:String,amount:Int){
        viewModelScope.launch(Dispatchers.IO) {
            val res = iOrder.getVariantByID(variantId)
            res.inventoryItemId?.let {
                Log.e("TAG", "adjustInventoryItem: $it $amount", )
                iOrder.adjustInventoryItem(it,amount) }
        }
    }

    private fun makeOrder(
        draftOrders: ArrayList<DraftOrder>,
        shippingAddress: ShippingAddress,
        user: User,
        discountCodes: ArrayList<DiscountCodes>
    ): Order {
        var orders = Order()
        orders.shippingAddress = shippingAddress
        orders.email = user.email

        if (discountCodes.size > 0) {
            discountCodes.forEach {
                orders.discountCodes.add(
                    DiscountCodes(
                        code = it.code,
                        amount = it.amount,
                        type = it.type
                    )
                )
            }
        }

        for (draftOrder in draftOrders) {
            orders.lineItems.add(
                LineItems(
                    quantity = draftOrder.lineItems[0].quantity,
                    variantId = draftOrder.lineItems[0].variantId,
                )
            )
            draftOrder.lineItems[0].quantity?.let {
                adjustInventoryItem(draftOrder.lineItems[0].variantId.toString(),
                    it
                )
            }
        }

        return orders
    }


}