package org.wit.swopzone.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


var lastOfferId=0L

internal fun getOfferId():Long{
    return lastOfferId++
}

class OfferMemStore: OfferStore, AnkoLogger {

    val offers = ArrayList<OfferModel>()

    override fun findAll() : List<OfferModel>{
        return offers
    }

    override fun create(offer: OfferModel){
        offer.id=getOfferId()
        offers.add(offer)
        logAll()
    }

    override fun delete(offer: OfferModel){
        offers.remove(offer)
    }

    override fun updateStatus(offerIn: OfferModel,newState: String){
        var foundOffer:OfferModel? = offers.find{p->p.id==offerIn.id}
        if (foundOffer!=null){
            foundOffer.offerState=newState
            logAll()
        }
    }

    fun logAll(){
        offers.forEach{info("${it}")}
    }
}