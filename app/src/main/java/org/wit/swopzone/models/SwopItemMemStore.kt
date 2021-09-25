package org.wit.swopzone.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastSwopItemId=0L

internal fun getSwopItemId():Long{
    return lastSwopItemId++
}

class SwopItemMemStore: SwopItemStore, AnkoLogger {

    val swopitems = ArrayList<SwopItemModel>()

    override fun findAll() : List<SwopItemModel>{
        return swopitems
    }

    override fun create(swopItem: SwopItemModel){
        swopItem.id= getSwopItemId()
        swopitems.add(swopItem)
        logAll()
    }

    override fun update(swopItem: SwopItemModel){
        var foundSwopItem: SwopItemModel? = swopitems.find{p->p.id==swopItem.id}
        if(foundSwopItem!=null){
            foundSwopItem.name=swopItem.name
            foundSwopItem.category=swopItem.category
            foundSwopItem.state=swopItem.state
            foundSwopItem.ownerId=swopItem.ownerId
            foundSwopItem.image=swopItem.image
            logAll()
        }
    }

    override fun delete(swopItem: SwopItemModel){
        swopitems.remove(swopItem)
    }

    fun logAll(){
        swopitems.forEach{info("${it}")}
    }
}