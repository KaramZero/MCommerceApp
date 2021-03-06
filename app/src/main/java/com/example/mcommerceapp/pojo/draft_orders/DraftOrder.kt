package draft_orders

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class DraftOrder(
    @SerializedName("id") var id: Long? = null,
    @SerializedName("note") var note: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("taxes_included") var taxesIncluded: Boolean? = null,
    @SerializedName("currency") var currency: String? = null,
    @SerializedName("invoice_sent_at") var invoiceSentAt: String? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("updated_at") var updatedAt: String? = null,
    @SerializedName("tax_exempt") var taxExempt: Boolean? = null,
    @SerializedName("completed_at") var completedAt: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("line_items") var lineItems: ArrayList<LineItems> = arrayListOf(),
    @SerializedName("shipping_address") var shippingAddress: String? = null,
    @SerializedName("billing_address") var billingAddress: String? = null,
    @SerializedName("invoice_url") var invoiceUrl: String? = null,
    @SerializedName("applied_discount") var appliedDiscount: String? = null,
    @SerializedName("order_id") var orderId: String? = null,
    @SerializedName("shipping_line") var shippingLine: String? = null,
    @SerializedName("tax_lines") var taxLines: ArrayList<TaxLines> = arrayListOf(),
    @SerializedName("tags") var tags: String? = null,
    @SerializedName("note_attributes") var noteAttributes: ArrayList<NoteAttributes> = arrayListOf(),
    @SerializedName("total_price") var totalPrice: String? = null,
    @SerializedName("subtotal_price") var subtotalPrice: String? = null,
    @SerializedName("total_tax") var totalTax: String? = null,
    @SerializedName("payment_terms") var paymentTerms: String? = null,
    @SerializedName("admin_graphql_api_id") var adminGraphqlApiId: String? = null,
    @SerializedName("customer") var customer: Customer? = Customer()

): Serializable