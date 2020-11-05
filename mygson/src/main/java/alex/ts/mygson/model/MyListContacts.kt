package alex.ts.mygson.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MyListContacts (
    val listContacts: List<MyContactModel>
): Parcelable