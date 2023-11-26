package com.AMiMa.shop.model

import com.AMiMa.shop.database.dataClasses.User

class CurrentUserInfo private constructor() {

    private var user: User? = null

    fun setUser(user: User) {
        this.user = user
    }

    fun getUser() = user

    companion object{
        private var instance: CurrentUserInfo? = null

        fun getInstance(): CurrentUserInfo{
            synchronized(this){
                if (instance == null)
                    instance = CurrentUserInfo()
                return instance!!
            }
        }
    }
}