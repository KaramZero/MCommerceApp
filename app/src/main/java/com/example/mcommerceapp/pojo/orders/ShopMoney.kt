package orders

import com.google.gson.annotations.SerializedName


data class ShopMoney (

  @SerializedName("amount"        ) var amount       : String? = null,
  @SerializedName("currency_code" ) var currencyCode : String? = null

)