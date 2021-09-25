package org.wit.swopzone.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_homepage.*
import org.jetbrains.anko.*
import org.wit.swopzone.R
import org.wit.swopzone.main.MainApp

class HomepageActivity : AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp
    val SIGNIN_REQUEST=1
    val JOIN_REQUEST=2
    val SWOPITEMSLIST_REQUEST=3
    val ITEM_SEARCH_REQUEST=4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)
        app=application as MainApp
        info("SwopZone Main Activity started...")

        btnSignIn.setOnClickListener(){
            startActivityForResult(intentFor<SignInActivity>(),SIGNIN_REQUEST)
        }

        btnJoin.setOnClickListener(){
            startActivityForResult(intentFor<JoinActivity>(),JOIN_REQUEST)
        }

        btnAllItemList.setOnClickListener(){
            startActivityForResult(intentFor<SwopItemsListActivity>()
                .putExtra("swopitemlist_all",179),SWOPITEMSLIST_REQUEST)
        }

        btnItemSearch.setOnClickListener(){
            startActivityForResult(intentFor<SearchItemActivity>()
                .putExtra("swopitemlist_search",179),ITEM_SEARCH_REQUEST)
        }

        // Add action bar and set title
        toolbarHomepage.title=this.title
        this.setSupportActionBar(toolbarHomepage)
    }

    override fun onCreateOptionsMenu(menu: Menu?):Boolean {
        menuInflater.inflate(R.menu.menu_homepage, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean{
       when(item?.itemId){
           R.id.item_exit_homepage ->{finish()}
       }
       return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
    }
}