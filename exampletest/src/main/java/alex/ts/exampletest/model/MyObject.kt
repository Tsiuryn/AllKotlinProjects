package alex.ts.exampletest.model

import com.google.gson.annotations.SerializedName

data class MyObject (
    @SerializedName("") var mainModel: MainModel,
    @SerializedName("") val list : List<Example>
)