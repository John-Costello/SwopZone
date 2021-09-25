package org.wit.swopzone.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_signin.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.swopzone.R
import org.wit.swopzone.main.MainApp
import org.wit.swopzone.models.UserModel

class SignInActivity : AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp
    val SIGNING_IN_REQUEST=3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        app=application as MainApp
        info("SwopZone Main Activity started...")

        btnSignIn.setOnClickListener(){
            var userEmail:String=email_signin.text.toString()
            var userPassword:String=password_signin.text.toString()
            if(app.usersMS.checkForUser(userEmail, userPassword))
            {
                var userSignIn:UserModel=app.usersMS.findUser(userEmail, userPassword)
                startActivityForResult(intentFor<MemberMainMenuActivity>().putExtra("userSignIn",userSignIn),SIGNING_IN_REQUEST)
            }
            else
            {
                toast("No member found with such username and password.")
            }
        }

        // Add action bar and set title
        toolbarSignIn.title="${this.title}"+" - SignIn"
        this.setSupportActionBar(toolbarSignIn)
    }

    override fun onCreateOptionsMenu(menu: Menu?):Boolean {
        menuInflater.inflate(R.menu.menu_signin, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        when(item?.itemId){
            R.id.item_cancel_signin ->{setResult(RESULT_OK);finish()}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode==-5){finish()}
        super.onActivityResult(requestCode, resultCode, data)
    }
}