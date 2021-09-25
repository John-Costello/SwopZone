package org.wit.swopzone.models

interface OfferStore {
    fun findAll(): List<OfferModel>
    fun create(offer: OfferModel)
    fun delete(offer: OfferModel)
    fun updateStatus(offerIn: OfferModel,newState: String)
}