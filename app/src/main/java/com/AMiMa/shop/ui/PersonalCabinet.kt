package com.AMiMa.shop.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import com.AMiMa.shop.R
import com.AMiMa.shop.model.CurrentUserInfo

class PersonalCabinet : AppCompatActivity(), TextWatcher {

    private val user = CurrentUserInfo.getInstance().getUser()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_cabinet)

        val login: TextView = findViewById(R.id.login)
        login.text = user?.name
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        TODO("Not yet implemented")
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        TODO("Not yet implemented")
    }

    override fun afterTextChanged(p0: Editable?) {

    }
}