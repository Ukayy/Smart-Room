package skripsi.uki.smartroom.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id:String?="",
    val id_card:String?="",
    val name:String?="",
    val password:String?="",
    val email:String?=""
):Parcelable