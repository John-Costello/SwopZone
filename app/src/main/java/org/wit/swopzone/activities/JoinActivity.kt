package org.wit.swopzone.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_join.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.wit.swopzone.R
import org.wit.swopzone.main.MainApp
import org.wit.swopzone.models.UserModel

class JoinActivity : AppCompatActivity(), AnkoLogger {

    var user = UserModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
        app=application as MainApp
        info("SwopZone Join Activity started...")

        btnJoin.setOnClickListener(){
            user.username=username_join.text.toString()
            user.email=email_join.text.toString()
            user.password=password_join.text.toString()
            var userPasswordRetyped:String=passwordRetyped_join.text.toString()
            user.location=location_join.text.toString()
            user.telephone=telephone_join.text.toString()
            var userInputVerifiedInvalid:Boolean=false
            if(user.username.length<3){userInputVerifiedInvalid=true
                                       toast("The username requires at least 3 characters.")}
            else if(user.email.length<3){userInputVerifiedInvalid=true
                toast("The user email address requires at least 3 characters.")}
            else if(user.password.length<3){userInputVerifiedInvalid=true
                toast("The user password requires at least 3 characters.")}
            else if(user.password!=userPasswordRetyped){userInputVerifiedInvalid=true
                toast("The password and retyped password must be the same.")}
            if(userInputVerifiedInvalid==false){
                app.usersMS.create(user.copy())
                toast("New member data stored.")
                setResult(RESULT_OK)
                finish()
            }
        }

        // Add action bar and set title
        toolbarJoin.title="${this.title}"+" - Join"
        this.setSupportActionBar(toolbarJoin)
    }

    override fun onCreateOptionsMenu(menu: Menu?):Boolean {
        menuInflater.inflate(R.menu.menu_join, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        when(item?.itemId){
            R.id.item_cancel_join ->{setResult(RESULT_OK);finish()}
        }
        return super.onOptionsItemSelected(item)
    }
}