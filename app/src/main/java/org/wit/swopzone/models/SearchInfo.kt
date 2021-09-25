package org.wit.swopzone.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchInfo(
                      var searchType:String="",
                      var name:String="",
                      var category:String=""):Parcelable