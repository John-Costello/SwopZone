package org.wit.swopzone.models

interface UserStore{
    fun findAll():List<UserModel>
    fun checkForUser(email:String, password:String):Boolean
    fun findUser(email:String, password:String):UserModel
    fun create(user: UserModel)
    fun update(user: UserModel)
    fun delete(user: UserModel)
}