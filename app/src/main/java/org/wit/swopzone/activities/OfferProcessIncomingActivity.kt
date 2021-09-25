package org.wit.swopzone.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_offerprocess_incoming.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import org.wit.swopzone.R
import org.wit.swopzone.main.MainApp
import org.wit.swopzone.models.OfferModel
import org.wit.swopzone.models.SwopItemModel

class OfferProcessIncomingActivity: AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp
    var offer= OfferModel()
    private var intentMode=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_offerprocess_incoming)
        app = application as MainApp


        if(intent.hasExtra("incoming_offer")){
            intentMode="incoming_offer"
            offer=intent.extras?.getParcelable<OfferModel>("incoming_offer")!!
            var giveItemId=offer.giveItemId
            var receiveItemId=offer.receiveItemId
            var giveItem:SwopItemModel? = app.swopitemsMS.findAll().find{p->p.id==giveItemId}
            if(giveItem!=null){
                var giveItemName= giveItem.name
                var giveItemCategory=giveItem.category
                swopGiveItemName.setText(giveItemName)
                swopGiveItemCategory.setText(giveItemCategory)
            }
            var receiveItem:SwopItemModel? = app.swopitemsMS.findAll().find{p->p.id==receiveItemId}
            if(receiveItem!=null){
                var receiveItemName=receiveItem.name
                var receiveItemCategory=receiveItem.category
                swopReceiveItemName.setText(receiveItemName)
                swopReceiveItemCategory.setText(receiveItemCategory)
            }
            location_lat.setText(offer.lat.toString())
            location_lng.setText(offer.lng.toString())
            location_zoom.setText(offer.zoom.toString())
            meeting_time.setText(offer.meetingTime)
            if(giveItem==null || receiveItem==null) {
                offer.offerState="invalid"
            }
            offer_status.setText(offer.offerState)
        }
        else
        {
            toast("error (offer process incoming activity)")
        }

        btnAcceptOffer.setOnClickListener(){
            if(offer.offerState!="Acknowledged as accepted"){
                offer.offerState="Accepted"
                app.offersMS.updateStatus(offer,"Accepted")
                offer_status.setText("Accept")
            }
        }

        btnDeclineOffer.setOnClickListener(){
            if(offer.offerState!="Acknowledged as declined"){
                offer.offerState="Declined"
                app.offersMS.updateStatus(offer,"Declined")
                offer_status.setText("Decline")
            }
        }

        // Add action bar and set title
        toolbarOfferProcessIncoming.title=this.title
        this.setSupportActionBar(toolbarOfferProcessIncoming)
    }

    override fun onCreateOptionsMenu(menu: Menu?):Boolean {
        menuInflater.inflate(R.menu.menu_offerprocess_incoming, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        when(item?.itemId){
            R.id.item_cancel_offerprocess_incoming->{finish()}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
    }


}
