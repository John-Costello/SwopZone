package org.wit.swopzone.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_makeanoffer.*
import org.jetbrains.anko.*
import org.wit.swopzone.R
import org.wit.swopzone.main.MainApp
import org.wit.swopzone.models.Location
import org.wit.swopzone.models.OfferModel
import org.wit.swopzone.models.SwopItemModel
import org.wit.swopzone.models.UserModel

class MakeAnOfferActivity : AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp
    var userSignedIn = UserModel()
    var giveSwopItem = SwopItemModel()
    var receiveSwopItem = SwopItemModel()
    var offer = OfferModel()
    val GIVE_ITEM_REQUEST=1
    val RECEIVE_ITEM_REQUEST=2
    val LOCATION_REQUEST=3


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userSignedIn = intent.extras?.getParcelable<UserModel>("userSignedIn")!!
        setContentView(R.layout.activity_makeanoffer)
        app = application as MainApp
        info("SwopZone Main Activity started...")

        btnSelectItemToGive.setOnClickListener(){
            startActivityForResult(intentFor<SwopItemsListActivity>()
                .putExtra("swopitemlist_selecting_give_item",userSignedIn),GIVE_ITEM_REQUEST)
        }

        btnSelectItemToRecieve.setOnClickListener(){
            startActivityForResult(intentFor<SwopItemsListActivity>()
           .putExtra("swopitemlist_selecting_receive_item",userSignedIn),RECEIVE_ITEM_REQUEST)
        }

        btnLocation.setOnClickListener(){
            info("Set Location Pressed")
            val location=Location(52.245696,-7.139102,15f)
            if(offer.zoom !=0f){
                location.lat=offer.lat
                location.lng=offer.lng
                location.zoom=offer.zoom

                location_lat.setText(offer.lat.toString())
                location_lng.setText(offer.lng.toString())
                location_zoom.setText(offer.zoom.toString())
            }
            startActivityForResult(intentFor<MapsActivity>()
                .putExtra("location_offer",location),LOCATION_REQUEST)
        }

        btnSubmitOffer.setOnClickListener() {
            var giveItemId: Long=giveSwopItem.id
            var receiveItemId: Long=receiveSwopItem.id
            var offerState: String="Pending"
            var meetingTime: String = meeting_time.text.toString()
            if(giveItemId>=0){
                if(receiveItemId>=0){
                    if(offer.zoom!=0f) {
                        if(meetingTime.length>0)
                        {
                            offer.giveItemId = giveItemId
                            offer.receiveItemId = receiveItemId
                            offer.offerState = offerState
                            offer.meetingTime = meetingTime
                            app.offersMS.create(offer)
                            finish()
                            toast("Offer has been saved.")
                        }
                        else
                        {
                            toast("A meeting time needs to be stated.")

                        }
                    }
                    else
                    {
                        toast("A location needs to be stated.")
                    }
                }
                else
                {
                    toast("A valid 'Receive' item needs to be selected.")
                }
            }
            else
            {
                toast("A valid 'Give' item needs to be selected.")
            }

        }
        // Add action bar and set title
        toolbarMakeAnOffer.title=this.title
        this.setSupportActionBar(toolbarMakeAnOffer)
    }

    override fun onCreateOptionsMenu(menu: Menu?):Boolean {
        menuInflater.inflate(R.menu.menu_makeanoffer, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        when(item?.itemId){
            R.id.item_cancel_makeanoffer->{setResult(-5);finish()}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            GIVE_ITEM_REQUEST -> {if(data!=null)
                {
                    if (data.hasExtra("giveItem")) {
                        giveSwopItem = data.extras?.getParcelable<SwopItemModel>("giveItem")!!
                        var name: String = giveSwopItem.name
                        var category: String = giveSwopItem.category
                        swopGiveItemName.setText(name)
                        swopGiveItemCategory.setText(category)
                    }
                }
            }
            RECEIVE_ITEM_REQUEST -> {if(data!=null)
                {
                     if (data.hasExtra("receiveItem")) {
                         receiveSwopItem = data.extras?.getParcelable<SwopItemModel>("receiveItem")!!
                         var name: String = receiveSwopItem.name
                         var category: String = receiveSwopItem.category
                         swopReceiveItemName.setText(name)
                         swopReceiveItemCategory.setText(category)
                     }
                }
            }
            LOCATION_REQUEST -> {if(data!=null)
                {val location=data.extras?.getParcelable<Location>("marker_location")!!
                    offer.lat = location.lat
                    offer.lng = location.lng
                    offer.zoom = location.zoom

                    location_lat.setText(offer.lat.toString())
                    location_lng.setText(offer.lng.toString())
                    location_zoom.setText(offer.zoom.toString())
                }
            }
        }
    }
}