package org.wit.swopzone.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_offerprocess_outgoing.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import org.wit.swopzone.R
import org.wit.swopzone.main.MainApp
import org.wit.swopzone.models.OfferModel
import org.wit.swopzone.models.SwopItemModel

class OfferProcessOutgoingActivity: AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp
    var offer= OfferModel()
    private var intentMode=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_offerprocess_outgoing)
        app = application as MainApp

        if(intent.hasExtra("outgoing_offer")){
            intentMode="outgoing_offer"
            offer=intent.extras?.getParcelable<OfferModel>("outgoing_offer")!!
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
            toast("error (offer process outgoing activity)")
        }

        if(offer.offerState=="Accepted" || offer.offerState=="Declined"){
            btnAcknowledge.visibility=View.VISIBLE
        }

        btnAcknowledge.setOnClickListener(){
            if(offer.offerState=="Accepted"){
                offer.offerState="Acknowledged as accepted"
                app.offersMS.updateStatus(offer,"Acknowledged as accepted")
                offer_status.setText("Acknowledge as accepted")
            }
            else if(offer.offerState=="Declined"){
                offer.offerState="Acknowledged as declined"
                app.offersMS.updateStatus(offer,"Acknowledged as declined")
                offer_status.setText("Acknowledge as declined")
            }
        }

        // Add action bar and set title
        toolbarOfferProcessOutgoing.title=this.title
        this.setSupportActionBar(toolbarOfferProcessOutgoing)
    }

    override fun onCreateOptionsMenu(menu: Menu?):Boolean {
        menuInflater.inflate(R.menu.menu_offerprocess_outgoing, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        when(item?.itemId){
            R.id.item_delete_offer -> {app.offersMS.delete(offer);
                toast("Offer deleted");finish()}
            R.id.item_cancel_offerprocess_outgoing->{finish()}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
    }
}
