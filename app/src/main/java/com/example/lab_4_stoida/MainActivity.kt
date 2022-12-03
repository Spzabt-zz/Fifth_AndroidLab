package com.example.lab_4_stoida

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.Toast
import android.content.Intent
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity(), AdapterListener {
    lateinit var nameEdit: EditText
    lateinit var emailEdit: EditText
    lateinit var insertButton: Button
    lateinit var recyclerView: RecyclerView
    private var userDatabase: UserDatabase? = null
    private var userDao: UserDao? = null
    private var userAdapter: UserAdapter? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userDatabase = UserDatabase.getInstance(this)
        userDao = userDatabase?.dao
        nameEdit = findViewById(R.id.name)
        emailEdit = findViewById(R.id.email)
        insertButton = findViewById(R.id.insert)
        recyclerView = findViewById(R.id.usersRecycleView)
        userAdapter = UserAdapter(this)
        recyclerView.adapter = userAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        insertButton.setOnClickListener(View.OnClickListener {
            val name = nameEdit.getText().toString()
            val email = emailEdit.getText().toString()
            val users = Users(0, name, email)
            userAdapter!!.addUser(users)
            userDao?.insert(users)
            nameEdit.setText("")
            emailEdit.setText("")
            Toast.makeText(this@MainActivity, "User inserted", Toast.LENGTH_SHORT).show()
        })
    }

    private fun fetchData() {
        userAdapter!!.clearData()
        val usersList = userDao!!.getAllUser()
        for (i in usersList.indices) {
            val users = usersList[i]
            users.let { userAdapter!!.addUser(it) }
        }
    }

    override fun OnUpdate(users: Users?) {
        val intent = Intent(this, UpdateActivity::class.java)
        intent.putExtra("model", users)
        startActivity(intent)
    }

    override fun OnDelete(id: Int, pos: Int) {
        userDao!!.delete(id)
        userAdapter!!.removeUser(pos)
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }
}