package org.wit.swopzone.activities

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_swopitem.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.swopzone.R
import org.wit.swopzone.helpers.readImage
import org.wit.swopzone.helpers.showImagePicker
import org.wit.swopzone.main.MainApp
import org.wit.swopzone.models.SwopItemModel
import org.wit.swopzone.models.UserModel

class SwopItemActivity: AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp
    private var signedInStatus:Boolean=false
    private var userSignedIn = UserModel()
    private var intentMode="view"
    private var swopItem = SwopItemModel()
    val IMAGE_REQUEST=1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swopitem)
        app=application as MainApp
        info("SwopZone Main Activity started...")

        if(intent.hasExtra("swopitem_view")){
            signedInStatus=false
            intentMode="view"
            swopItem=intent.extras?.getParcelable<SwopItemModel>("swopitem_view")!!
            swopItemName.setTextIsSelectable(false)
            swopItemName.setFocusable(false)
            swopItemName.setClickable(false)
            swopItemName.setInputType(InputType.TYPE_NULL)
            swopItemCategory.setTextIsSelectable(false)
            swopItemCategory.setFocusable(false)
            swopItemCategory.setInputType(InputType.TYPE_NULL)

        }
        else if(intent.hasExtra("swopitem_add")){
            signedInStatus=true
            userSignedIn = intent.extras?.getParcelable<UserModel>("userSignedIn")!!
            intentMode="add"
            btnAdd.setVisibility(View.VISIBLE)
            btnChooseImage.setVisibility(View.VISIBLE)
        }
        else if(intent.hasExtra("swopitem_edit")){
            signedInStatus=true
            userSignedIn = intent.extras?.getParcelable<UserModel>("userSignedIn")!!
            intentMode="edit"
            swopItem=intent.extras?.getParcelable<SwopItemModel>("swopitem_edit")!!
            btnAdd.setVisibility(View.VISIBLE)
            btnAdd.setText("Save Item")
            btnChooseImage.setVisibility(View.VISIBLE)
            if(swopItem.image!=""){
                btnChooseImage.setText("Change Image")
            }
        }

        //.putExtra("swopitem_edit",swopItem)

        if(intentMode=="view"||intentMode=="edit"){
            //swopItem=intent.extras?.getParcelable<SwopItemModel>("swopitem_view")!!
            swopItemName.setText(swopItem.name)
            swopItemCategory.setText(swopItem.category)
            swopItemImage.setText(swopItem.image)
        }

        btnAdd.setOnClickListener(){
            swopItem.name = swopItemName.text.toString()
            swopItem.category = swopItemCategory.text.toString()
            swopItem.ownerId=userSignedIn.id
            if(swopItem.name.isNotEmpty()){
                if(intentMode=="edit"){
                    app.swopitemsMS.update(swopItem.copy())
                }else if(intentMode=="add"){
                    app.swopitemsMS.create(swopItem.copy())
                }
                setResult(RESULT_OK)
                finish()
            }
            else
            {
                toast("Please enter a swop item name")
            }
        }

        btnChooseImage.setOnClickListener(){
            info("Select Image")
            showImagePicker(this,IMAGE_REQUEST)
        }

        // Add action bar and set title
        toolbarSwopItem.title=this.title
        this.setSupportActionBar(toolbarSwopItem)
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_swopitem, menu)
        if(intentMode=="edit" && menu != null) menu.getItem(0).setVisible(true)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.item_delete_swopitem -> {app.swopitemsMS.delete(swopItem);
                toast("Item deleted");finish()}
            R.id.item_cancel_swopitem -> {setResult(RESULT_OK);finish()}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){

        when(requestCode){
            IMAGE_REQUEST -> {
                if(data!=null){
                    swopItem.image=data.getData().toString()
                    imgSwopItemImage.setImageBitmap(readImage(this,resultCode,data))
                    btnChooseImage.setText("Change Image")
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


}