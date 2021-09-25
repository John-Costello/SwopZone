package org.wit.swopzone.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(var id:Long=-1,
                        var username:String="",
                        var email:String="",
                        var password:String="",
                        var location:String="",
                        var telephone:String=""):Parcelable

