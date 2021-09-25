package org.wit.swopzone.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.swopzone.models.*

class MainApp:Application(), AnkoLogger {

    lateinit var usersMS: UserStore
    lateinit var swopitemsMS: SwopItemStore
    lateinit var offersMS: OfferStore

    override fun onCreate() {
        super.onCreate()
        usersMS=UserMemStore()
        swopitemsMS=SwopItemMemStore()
        offersMS=OfferMemStore()
        info("SwopZone started...")

        usersMS.create(UserModel(username="Bob",email="Bob",password="Bob",location="Bob",telephone="051"))
        usersMS.create(UserModel(username="Tom",email="Tom",password="Tom",location="Tom",telephone="052"))
        swopitemsMS.create(SwopItemModel(name="Bike",category= "Vehicle",state="",ownerId=0,image=""))
        swopitemsMS.create(SwopItemModel(name="Table",category="Furniture",state="",ownerId=1,image=""))
        swopitemsMS.create(SwopItemModel(name="Chair",category= "Furniture",state="",ownerId=1,image=""))
        swopitemsMS.create(SwopItemModel(name="Cup",category= "Pottery",state="",ownerId=1,image=""))

    }
}