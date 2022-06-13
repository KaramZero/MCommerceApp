package com.example.mcommerceapp.view.ui.shopping_cart.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mcommerceapp.R
import draft_orders.DraftOrder

class CartItemsAdapter(
    private var cartList: ArrayList<DraftOrder>,
    private var communicator: CartCommunicator,
    private var myContext: Context
) :
    RecyclerView.Adapter<CartItemsAdapter.ViewHolder>() {

    fun setOrders(cartList: ArrayList<DraftOrder>) {
        this.cartList = cartList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)

        val view: View =
            layoutInflater.inflate(R.layout.item_shopping_cart, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = cartList.get(position).lineItems.get(0).name.toString()
        holder.image.clipToOutline = true
//        Glide.with(myContext)
//            .load(cartList.get(position).noteAttributes.get(0).name)
//            .into(holder.image)

        var quantity = cartList.get(position).lineItems.get(0).quantity!!
        var price = cartList.get(position).lineItems.get(0).price.toString().toDouble()

        holder.countTx.text = quantity.toString()
        holder.value.text = String.format("%.2f", price * quantity)

        if (quantity == 1) {
            holder.minusBt.visibility = View.INVISIBLE
        }

        holder.minusBt.setOnClickListener {
            quantity--
            if (quantity <= 1) {
                holder.countTx.text = (quantity).toString()
                holder.value.text = String.format("%.2f", price * quantity)
                holder.minusBt.visibility = View.INVISIBLE
            } else {
                holder.countTx.text = (quantity).toString()
                holder.value.text = String.format("%.2f", price * quantity)
            }
        }

        holder.plusBt.setOnClickListener {
            holder.minusBt.visibility = View.VISIBLE
            quantity++
            holder.countTx.text = (quantity).toString()
            holder.value.text = String.format("%.2f", price * quantity)

            //communicator
        }

        holder.deleteBt.setOnClickListener {

        }

    }

    override fun getItemCount(): Int {
        return cartList.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.item_image)
        var minusBt: ImageButton = itemView.findViewById(R.id.minus_bt)
        var plusBt: ImageButton = itemView.findViewById(R.id.plus_bt)
        var countTx: TextView = itemView.findViewById(R.id.item_count_tx)
        var name: TextView = itemView.findViewById(R.id.item_name_tx)
        var value: TextView = itemView.findViewById(R.id.item_value_tx)
        var deleteBt: ImageButton = itemView.findViewById(R.id.item_delete_bt)
    }

}