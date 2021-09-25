package org.wit.swopzone.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_membermainmenu.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.wit.swopzone.R
import org.wit.swopzone.main.MainApp
import org.wit.swopzone.models.UserModel

class MemberMainMenuActivity: AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp
    var userSignedIn = UserModel()
    val OUTGOING_OFFERS_REQUEST=1
    val INCOMING_OFFERS_REQUEST=2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userSignedIn = intent.extras?.getParcelable<UserModel>("userSignIn")!!
        setContentView(R.layout.activity_membermainmenu)
        app=application as MainApp

        info("SwopZone Member Main Menu Activity started...")

        var memberName="Member: "+userSignedIn.username
        textMemberName.setText(memberName)


        btnMyItems.setOnClickListener(){
            startActivityForResult(intentFor<SwopItemsListActivity>()
                .putExtra("swopitemlist_member",147)
                .putExtra("userSignedIn",userSignedIn),0)
        }

        btnMakeAnOffer.setOnClickListener(){
            startActivityForResult(intentFor<MakeAnOfferActivity>()
                .putExtra("userSignedIn",userSignedIn),0)
        }

        btnOutgoingOffers.setOnClickListener(){
            startActivityForResult(intentFor<OfferListActivity>()
                .putExtra("userSignedIn",userSignedIn)
                .putExtra("outgoing_offer",577),OUTGOING_OFFERS_REQUEST)
        }

        btnIncomingOffers.setOnClickListener(){
            startActivityForResult(intentFor<OfferListActivity>()
                .putExtra("userSignedIn",userSignedIn)
                .putExtra("incoming_offer",555),INCOMING_OFFERS_REQUEST)
        }

        // Add action bar and set title
        toolbarMemberMainMenu.title=this.title
        this.setSupportActionBar(toolbarMemberMainMenu)
    }

    override fun onCreateOptionsMenu(menu: Menu?):Boolean {
        menuInflater.inflate(R.menu.menu_membermainmenu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        when(item?.itemId){
            R.id.item_signout_membermainmenu->{setResult(-5);finish()}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}