package org.wit.swopzone.models

interface SwopItemStore {
    fun findAll(): List<SwopItemModel>
    fun create(swopItem: SwopItemModel)
    fun update(swopItem: SwopItemModel)
    fun delete(swopItem: SwopItemModel)
}