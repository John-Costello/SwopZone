package org.wit.swopzone.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_swopitems_list.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.intentFor
import org.wit.swopzone.R
import org.wit.swopzone.main.MainApp
import org.wit.swopzone.models.SearchInfo
import org.wit.swopzone.models.SwopItemListener
import org.wit.swopzone.models.SwopItemModel
import org.wit.swopzone.models.UserModel

class SwopItemsListActivity: AppCompatActivity(), SwopItemListener, AnkoLogger {

    lateinit var app: MainApp
    private var signedInStatus:Boolean=false
    private var userSignedIn = UserModel()
    private var userId:Long=0
    private var searchType:String=""
    private var searchWord:String=""

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swopitems_list)
        app=application as MainApp

        if(intent.hasExtra("swopitemlist_all")){
            signedInStatus=false
        }
        else if(intent.hasExtra("swopitemlist_searchresult")){
            signedInStatus=false
            if(intent.hasExtra("search_name")){
                searchType="Name";
                var searchinfo=intent.extras?.getParcelable<SearchInfo>("search_name")
                if(searchinfo!=null){searchWord=searchinfo.name}
            }
            if(intent.hasExtra("search_category")){
                searchType="Category";
                var searchinfo=intent.extras?.getParcelable<SearchInfo>("search_category")
                if(searchinfo!=null){searchWord=searchinfo.category}
            }

        }
        else if(intent.hasExtra("swopitemlist_member")){
            signedInStatus=true
            userSignedIn = intent.extras?.getParcelable<UserModel>("userSignedIn")!!
        }
        else if(intent.hasExtra("swopitemlist_selecting_give_item")){
            signedInStatus=true
            searchType="giveItem"
            userSignedIn = intent.extras?.getParcelable<UserModel>("swopitemlist_selecting_give_item")!!
            this.userId=userSignedIn.id
        }
        else if(intent.hasExtra("swopitemlist_selecting_receive_item")){
            signedInStatus=true
            searchType="receiveItem"
            userSignedIn = intent.extras?.getParcelable<UserModel>("swopitemlist_selecting_receive_item")!!
            this.userId=userSignedIn.id
        }

        //layout and populate for display
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager=layoutManager

        if(signedInStatus==false){
            if(searchType=="Name")
            {
                loadSearchSwopItems("Name",searchWord)
            }
            else if(searchType=="Category") {
                loadSearchSwopItems("Category",searchWord)
            }
            else{
                loadSwopItems()
            }
        }
        else
        {
            if(searchType=="giveItem"){
                loadOfferSwopItems("giveItem",userId)

            }
            else if(searchType=="receiveItem"){
                loadOfferSwopItems("receiveItem",userId)
            }
            else {
                loadMemberSwopItems(userSignedIn)
            }
        }


        toolbarSwopItemsList.title = "${this.title}"
        setSupportActionBar(toolbarSwopItemsList)
    }

    override fun onCreateOptionsMenu(menu: Menu?):Boolean{
        menuInflater.inflate(R.menu.menu_swopitemslist,menu)
        if(signedInStatus==true && menu != null) {
            if(searchType!="giveItem" && searchType!="receiveItem"){
                menu.getItem(0).setVisible(true)
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        when(item.itemId){
            R.id.item_add_swopitemslist -> {startActivityForResult(intentFor<SwopItemActivity>()
                .putExtra("swopitem_add",159)
                .putExtra("userSignedIn",userSignedIn),0)}
            R.id.item_cancel_swopitemslist -> finish()/*startActivityForResult<SwopItemActivity>(0)*/
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSwopItemClick(swopItem: SwopItemModel){
        if(signedInStatus==false){
            startActivityForResult(intentFor<SwopItemActivity>()
                .putExtra("swopitem_view",swopItem),0)}
        else{
            if(searchType=="giveItem"){
                var resultIntent=Intent()
                resultIntent.putExtra("giveItem",swopItem)
                setResult(Activity.RESULT_OK,resultIntent)
                finish()
            }
            else if(searchType=="receiveItem"){
                var resultIntent=Intent()
                resultIntent.putExtra("receiveItem",swopItem)
                setResult(Activity.RESULT_OK,resultIntent)
                finish()
            }
            else
            startActivityForResult(intentFor<SwopItemActivity>()
                .putExtra("swopitem_edit",swopItem)
                .putExtra("userSignedIn",userSignedIn),0)}
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(signedInStatus==false){
            if(searchType=="Name")
            {
                loadSearchSwopItems("Name",searchWord)
            }
            else if(searchType=="Category") {
                loadSearchSwopItems("Category",searchWord)
            }
            else{
                loadSwopItems()
            }
        }else
        {
            loadMemberSwopItems(userSignedIn)
        }

        if(intent.hasExtra("swopitemlist_view")){

        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun loadSwopItems(){
        showSwopItems(app.swopitemsMS.findAll())
    }

    fun showSwopItems(swopitems_in:List<SwopItemModel>){
        recyclerView.adapter = SwopItemAdapter(swopitems_in, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun loadMemberSwopItems(userSignedIn: UserModel){
        showMemberSwopItems(userSignedIn, app.swopitemsMS.findAll())

    }

    fun showMemberSwopItems(userSignedIn: UserModel, swopitems_in:List<SwopItemModel>){
        var memberSwopitems=ArrayList<SwopItemModel>()
        var userId:Long=userSignedIn.id
        for(swopitem in swopitems_in){
            if(swopitem.ownerId==userId){
                memberSwopitems.add(swopitem)
            }
        }
        recyclerView.adapter = SwopItemAdapter(memberSwopitems, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun loadSearchSwopItems(searchType:String, searchWord:String){
        showSearchSwopItems(searchType, searchWord, app.swopitemsMS.findAll())
    }

    fun showSearchSwopItems(searchType: String, searchWord: String, swopitems_in:List<SwopItemModel>){
        var searchResultSwopitems=ArrayList<SwopItemModel>()
        if(searchType=="Name"){
            for(swopitem in swopitems_in){
                if(swopitem.name.trim().equals(searchWord.trim(),ignoreCase=true)){
                    searchResultSwopitems.add(swopitem)
                }
            }
        }
        else if(searchType=="Category"){
            for(swopitem in swopitems_in){
                if(swopitem.category.trim().equals(searchWord.trim(),ignoreCase=true)){
                    searchResultSwopitems.add(swopitem)
                }
            }
        }
        recyclerView.adapter = SwopItemAdapter(searchResultSwopitems, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun loadOfferSwopItems(searchType:String,userId:Long){
        showOfferSwopItems(searchType, userId, app.swopitemsMS.findAll())
    }

    fun showOfferSwopItems(offerType:String, userId:Long, swopitems_in:List<SwopItemModel>) {
        var offerSwopitems = ArrayList<SwopItemModel>()
        if (offerType == "giveItem"){
            for (swopitem in swopitems_in) {
                if (swopitem.ownerId == userId) {
                    offerSwopitems.add(swopitem)
                }
            }
        }
        else if(offerType=="receiveItem"){
            for (swopitem in swopitems_in) {
                if (swopitem.ownerId != userId) {
                    offerSwopitems.add(swopitem)
                }
            }
        }
        recyclerView.adapter = SwopItemAdapter(offerSwopitems, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }
}