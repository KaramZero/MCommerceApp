package draft_orders

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class LineItems (

  @SerializedName("id"                   ) var id                 : Int?                = null,
  @SerializedName("variant_id"           ) var variantId          : Long?                = null,
  @SerializedName("product_id"           ) var productId          : Long?                = null,
  @SerializedName("title"                ) var title              : String?             = null,
  @SerializedName("variant_title"        ) var variantTitle       : String?             = null,
  @SerializedName("sku"                  ) var sku                : String?             = null,
  @SerializedName("vendor"               ) var vendor             : String?             = null,
  @SerializedName("quantity"             ) var quantity           : Int?                = null,
  @SerializedName("requires_shipping"    ) var requiresShipping   : Boolean?            = null,
  @SerializedName("taxable"              ) var taxable            : Boolean?            = null,
  @SerializedName("gift_card"            ) var giftCard           : Boolean?            = null,
  @SerializedName("fulfillment_service"  ) var fulfillmentService : String?             = null,
  @SerializedName("grams"                ) var grams              : Int?                = null,
  @SerializedName("tax_lines"            ) var taxLines           : ArrayList<TaxLines> = arrayListOf(),
  @SerializedName("applied_discount"     ) var appliedDiscount    : String?             = null,
  @SerializedName("name"                 ) var name               : String?             = null,
  @SerializedName("properties"           ) var properties         : ArrayList<String>   = arrayListOf(),
  @SerializedName("custom"               ) var custom             : Boolean?            = null,
  @SerializedName("price"                ) var price              : String?             = null,
  @SerializedName("admin_graphql_api_id" ) var adminGraphqlApiId  : String?             = null


): Serializable