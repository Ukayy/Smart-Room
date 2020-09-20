package skripsi.uki.smartroom.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Users(
    var id_card:String?="",
    var name:String?="",
    var password:String?="",
    var email:String?=""
):Parcelable

@Parcelize
data class User(
    var angka: String,
    var id_card:String?="",
    var name:String?="",
    var password:String?="",
    var email:String?=""
):Parcelable