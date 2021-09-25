package org.wit.swopzone.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastUserId=0L

internal fun getUserId():Long{
    return lastUserId++
}

class UserMemStore:UserStore, AnkoLogger {

    val users = ArrayList<UserModel>()

    override fun findAll(): List<UserModel>{
        return users
    }

    override fun checkForUser(email:String, password:String): Boolean{
        var foundUser:UserModel? = users.find{u->(u.email==email && u.password==password)}
        return (foundUser != null)
    }

    override fun findUser(email:String, password:String): UserModel{
        var foundUser:UserModel? = users.find{u->(u.email==email && u.password==password)}
        return foundUser!!
    }

    override fun create(user: UserModel){
        user.id= getUserId()
        users.add(user)
    }

    override fun update(userIn: UserModel){
        var foundUser: UserModel? = users.find{p->p.id==userIn.id}
        if(foundUser!=null){
            foundUser.username=userIn.username
            foundUser.email=userIn.email
            foundUser.password=userIn.password
            foundUser.location=userIn.location
            foundUser.telephone=userIn.telephone
            logAll()
        }
    }

    override fun delete(user: UserModel){
        users.remove(user)
    }

    fun logAll(){
        users.forEach{info("${it}")}
    }
}