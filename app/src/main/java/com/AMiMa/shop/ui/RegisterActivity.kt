package com.AMiMa.shop.ui

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RegisterActivity : AppCompatActivity(), View.OnFocusChangeListener {

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
        usernameView.onFocusChangeListener = this
        userPasswordView = findViewById(R.id.registerUserPassword)
        userPasswordView.onFocusChangeListener = this
        userRepeatPasswordView = findViewById(R.id.registerRepeatUserPassword)
        userRepeatPasswordView.onFocusChangeListener = this
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
                textError = "Поля не должны быть пустыми"
            else if (userPassword != userRepeatPassword)
                textError = "Пароли не совпадают"
            else if (database.getUser(username)?.name == username)
                textError = "Такой логин уже занят"

            if (textError.isNullOrEmpty())
                database.insertUser(User(username, userPassword))
            else
                runOnUiThread { textErrorView.text = textError }
        }
    }

    override fun onFocusChange(view: View?, focus: Boolean) {
        initInputText()
        when (view?.id) {
            R.id.registerUserName -> {
                if (username == getString(R.string.username)) {
                    usernameView.setTextColor(ContextCompat.getColor(this, R.color.black))
                    usernameView.setText("")
                }
            }
            R.id.registerUserPassword -> {
                if (userPassword == getString(R.string.password)) {
                    userPasswordView.setTextColor(ContextCompat.getColor(this, R.color.black))
                    userPasswordView.setText("")
                }
            }
            R.id.registerRepeatUserPassword -> {
                if (userRepeatPassword == getString(R.string.repeatPassword)) {
                    userRepeatPasswordView.setTextColor(ContextCompat.getColor(this, R.color.black))
                    userRepeatPasswordView.setText("")
                }
            }
        }
    }

    private fun initInputText(){
        username = usernameView.text.toString()
        userPassword = userPasswordView.text.toString()
        userRepeatPassword = userRepeatPasswordView.text.toString()
    }
}