package alex.ts.mygson.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MyContactModel (
    val id: Int,
    val name: String,
    val date: String
): Parcelable