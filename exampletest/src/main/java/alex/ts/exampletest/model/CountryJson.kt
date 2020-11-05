package alex.ts.exampletest.model

import com.google.gson.annotations.SerializedName

data class CountryJson(
   @SerializedName("iso2Code") val iso: String,
   @SerializedName("name") val name: String
)