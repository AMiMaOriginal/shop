package com.AMiMa.shop.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.AMiMa.shop.R
import com.AMiMa.shop.database.Database
import com.AMiMa.shop.database.dao.UserDao
import com.AMiMa.shop.database.dataClasses.User
import com.AMiMa.shop.model.CurrentUserInfo
import com.AMiMa.shop.model.WorkInCart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var usernameView: EditText
    private lateinit var userPasswordView: EditText
    private lateinit var userRepeatPasswordView: EditText
    private lateinit var registerUserView: Button
    private lateinit var textErrorView: TextView
    private lateinit var database: UserDao

    private lateinit var username: String
    private lateinit var userPassword: String
    private lateinit var userRepeatPassword: String
    private var textError: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        database = Database.getDatabase(this)!!.usersDao()

        usernameView = findViewById(R.id.registerUserName)
        userPasswordView = findViewById(R.id.registerUserPassword)
        userRepeatPasswordView = findViewById(R.id.registerRepeatUserPassword)
        userRepeatPasswordView.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                addUser()
                return@OnKeyListener true
            }
            false
        })
        registerUserView = findViewById(R.id.registerUser)
        registerUserView.setOnClickListener { addUser() }
        textErrorView = findViewById(R.id.registerTextError)
    }

    private fun addUser() = runBlocking{
        initInputText()

        launch(Dispatchers.IO) {
            if (username.trim().isEmpty() || userPassword.trim().isEmpty() || userRepeatPassword.trim().isEmpty())
                runOnUiThread { textErrorView.text = "Поля не должны быть пустыми"}
            else if (userPassword != userRepeatPassword)
                runOnUiThread { textErrorView.text = "Пароли не совпадают" }
            else if (database.getUser(username)?.name == username)
                runOnUiThread { textErrorView.text = "Такой логин занят" }
            else {
                database.insertUser(User(username, userPassword))
                CurrentUserInfo.getInstance().setUser(User(username, userPassword))
                startActivity(Intent(this@RegisterActivity, ListProducts::class.java))
            }
        }
    }

    private fun initInputText(){
        username = usernameView.text.toString()
        userPassword = userPasswordView.text.toString()
        userRepeatPassword = userRepeatPasswordView.text.toString()
    }

    fun logIn(view: View?){
        startActivity(Intent(this, LogIn::class.java))
    }
}