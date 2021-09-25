package org.wit.swopzone.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_offer_list.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.swopzone.R
import org.wit.swopzone.main.MainApp
import org.wit.swopzone.models.*

class OfferListActivity: AppCompatActivity(), OfferListener, AnkoLogger {

    lateinit var app: MainApp
    var userSignedIn = UserModel()

    private var intentMode=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userSignedIn = intent.extras?.getParcelable<UserModel>("userSignedIn")!!
        setContentView(R.layout.activity_offer_list)
        app = application as MainApp

        //layout and populate for display
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager


        if(intent.hasExtra("outgoing_offer")){
            intentMode="outgoing_offer"
            loadMyOffers(userSignedIn.id)
        }
        else if(intent.hasExtra("incoming_offer")){
            intentMode="incoming_offer"
            loadIncomingOffers(userSignedIn.id)
        }

        toolbarOfferList.title = this.title
        setSupportActionBar(toolbarOfferList)
    }

    override fun onCreateOptionsMenu(menu: Menu?):Boolean{
        menuInflater.inflate(R.menu.menu_offerlist,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        when(item.itemId){
            R.id.item_cancel_offerlist -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onOfferClick(offer: OfferModel) {
        if(intentMode=="outgoing_offer"){
            startActivityForResult(intentFor<OfferProcessOutgoingActivity>()
                .putExtra("outgoing_offer",offer),0)
        }
        else if(intentMode=="incoming_offer"){
            startActivityForResult(intentFor<OfferProcessIncomingActivity>()
                .putExtra("incoming_offer",offer),0)
        }
        else
        {
            toast("error")
        }
    }

    override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?){

        if(intent.hasExtra("outgoing_offer")){
            intentMode="outgoing_offer"
            loadMyOffers(userSignedIn.id)
        }
        else if(intent.hasExtra("incoming_offer")){
            intentMode="incoming_offer"
            loadIncomingOffers(userSignedIn.id)
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun loadMyOffers(userId:Long){
        showMyOffers(userId,app.offersMS.findAll(),app.swopitemsMS.findAll())
    }

    private fun loadIncomingOffers(userId:Long){
        showIncomingOffers(userId,app.offersMS.findAll(),app.swopitemsMS.findAll())
    }

    fun showMyOffers(userId:Long,offers_in:List<OfferModel>,
                     swopItems_in:List<SwopItemModel>){
        var myOffers=ArrayList<OfferModel>()
        for(offer in offers_in){
            val giveItemId=offer.giveItemId
            var foundItem:SwopItemModel? = swopItems_in.find{p->p.id==giveItemId}
            if(foundItem!=null) {
                var ownerId: Long = foundItem.ownerId
                if(ownerId==userId){
                    myOffers.add(offer)
                }
            }
        }
        var swopItemsIn=app.swopitemsMS.findAll()
        recyclerView.adapter = OfferAdapter(userSignedIn,swopItemsIn,myOffers,this)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    fun showIncomingOffers(userId:Long,offers_in:List<OfferModel>,
                           swopItems_in:List<SwopItemModel>){
        var incomingOffers=ArrayList<OfferModel>()
        for(offer in offers_in){
            val receiveItemId=offer.receiveItemId
            var foundItem:SwopItemModel? = swopItems_in.find{p->p.id==receiveItemId}
            if(foundItem!=null) {
                var ownerId: Long = foundItem.ownerId
                if(ownerId==userId){
                    incomingOffers.add(offer)
                }
            }
        }
        var swopItemsIn=app.swopitemsMS.findAll()
        recyclerView.adapter = OfferAdapter(userSignedIn,swopItemsIn,incomingOffers,this)
        recyclerView.adapter?.notifyDataSetChanged()
    }

}