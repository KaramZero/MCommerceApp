package draft_orders

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class SmsMarketingConsent (

  @SerializedName("state"                  ) var state                : String? = null,
  @SerializedName("opt_in_level"           ) var optInLevel           : String? = null,
  @SerializedName("consent_updated_at"     ) var consentUpdatedAt     : String? = null,
  @SerializedName("consent_collected_from" ) var consentCollectedFrom : String? = null

): Serializable