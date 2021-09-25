package org.wit.swopzone.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SwopItemModel(var id:Long=-1,
                         var name:String="",
                         var category:String="",
                         var state:String="",
                         var ownerId:Long=-1,
                         var image:String=""):Parcelable



