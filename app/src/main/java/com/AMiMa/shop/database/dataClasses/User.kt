package com.AMiMa.shop.database.dataClasses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(val name: String, val password: String, var admin: Boolean = false, @PrimaryKey(autoGenerate = true) val id: Int? = null)
