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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LogIn : AppCompatActivity() {

    private lateinit var usernameView: EditText
    private lateinit var passwordView: EditText
    private lateinit var textError: TextView
    private lateinit var logIn: Button
    private lateinit var username: String
    private lateinit var password: String
    private lateinit var database: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        database = Database.getDatabase(this)!!.usersDao()

        usernameView = findViewById(R.id.LogInUsername)
        passwordView = findViewById(R.id.LogInPassword)
        passwordView.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                logIn()
                return@OnKeyListener true
            }
            false
        })
        logIn = findViewById(R.id.LogInUser)
        logIn.setOnClickListener{ logIn() }
        textError = findViewById(R.id.LogInError)
    }

    private fun logIn() = runBlocking {
        initInputText()

        launch(Dispatchers.IO) {
            val user = database.getUser(username)
            if (!User(username, password).equals(user))
                runOnUiThread { textError.text = "Неправильный логин или пароль" }
            else {
                CurrentUserInfo.getInstance().setUser(database.getUser(username)!!)
                startActivity(Intent(this@LogIn, ListProducts::class.java))
            }
        }
    }

    private fun initInputText(){
        username = usernameView.text.toString()
        password = passwordView.text.toString()
    }

    fun register(view: View?){
        startActivity(Intent(this, RegisterActivity::class.java))
    }
}