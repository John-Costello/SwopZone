package org.wit.swopzone.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_searchitem.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.wit.swopzone.R
import org.wit.swopzone.main.MainApp
import org.wit.swopzone.models.SearchInfo

class SearchItemActivity : AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp
    val SEARCH_ITEMS = 1
    var searchinfo=SearchInfo()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchitem)
        app = application as MainApp
        info("SwopZone Main Activity started...")

        btnSearch.setOnClickListener() {
            var searchWord:String=edittextSearchItem.text.toString()
            if(radio_id1.isChecked){
                searchinfo.searchType="Name"
                searchinfo.name=searchWord
                startActivityForResult(intentFor<SwopItemsListActivity>()
                    .putExtra("swopitemlist_searchresult",179)
                    .putExtra("search_name",searchinfo), SEARCH_ITEMS)
            }
            else if(radio_id2.isChecked){
                searchinfo.searchType="Category"
                searchinfo.category=searchWord
                startActivityForResult(intentFor<SwopItemsListActivity>()
                    .putExtra("swopitemlist_searchresult",179)
                    .putExtra("search_category",searchinfo), SEARCH_ITEMS)
            }
        }

        // Add action bar and set title
        toolbarSearchItem.title=this.title
        this.setSupportActionBar(toolbarSearchItem)
    }

    override fun onCreateOptionsMenu(menu: Menu?):Boolean {
        menuInflater.inflate(R.menu.menu_searchitem, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        when(item?.itemId){
            R.id.item_cancel_searchitem->{setResult(-5);finish()}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
    }
}

