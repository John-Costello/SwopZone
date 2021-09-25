package org.wit.swopzone.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OfferModel (var id:Long=-1,
                       var giveItemId:Long=-1,
                       var receiveItemId:Long=-1,
                       var offerState:String="",
                       var lat: Double=0.0,
                       var lng: Double=0.0,
                       var zoom: Float=0f,
                       var meetingTime: String=""): Parcelable

