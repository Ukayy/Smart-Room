package skripsi.uki.smartroom.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Session (
    var device_key :String? ="",
    var username : String? ="",
    var password  : String?="",
    var role :String? =""
):Parcelable