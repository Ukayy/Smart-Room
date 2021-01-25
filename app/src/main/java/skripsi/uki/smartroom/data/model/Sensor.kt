package skripsi.uki.smartroom.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Sensor(

    var date:Float,
    var value:Float

): Parcelable