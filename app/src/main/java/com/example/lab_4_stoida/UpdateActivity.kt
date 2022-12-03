package com.example.lab_4_stoida

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.os.Bundle
import android.view.View
import android.widget.Button

class UpdateActivity : AppCompatActivity() {
    private lateinit var nameEdit: EditText
    private lateinit var emailEdit: EditText
    private lateinit var updateButton: Button
    private var users: Users? = null
    private var userDatabase: UserDatabase? = null
    private var userDao: UserDao? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        userDatabase = UserDatabase.getInstance(this)
        userDao = userDatabase?.dao
        nameEdit = findViewById(R.id.name)
        emailEdit = findViewById(R.id.email)
        updateButton = findViewById(R.id.update)
        users = intent.getSerializableExtra("model") as Users?
        nameEdit.setText(users!!.name)
        emailEdit.setText(users!!.email)
        updateButton.setOnClickListener(View.OnClickListener {
            val userModel =
                Users(users!!.id, nameEdit.text.toString(), emailEdit.text.toString())
            userDao?.update(userModel)
            finish()
        })
    }
}