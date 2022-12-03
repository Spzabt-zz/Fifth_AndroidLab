package com.example.lab_4_stoida

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab_4_stoida.api.UserApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity(),
    AdapterListener {
    lateinit var nameEdit: EditText
    lateinit var cityEdit: EditText
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
        cityEdit = findViewById(R.id.city)
        insertButton = findViewById(R.id.insert)
        recyclerView = findViewById(R.id.usersRecycleView)

        userAdapter = UserAdapter(this)

        recyclerView.adapter = userAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        insertButton.setOnClickListener(View.OnClickListener {
            val name = nameEdit.text.toString()
            val email = cityEdit.text.toString()
            val users = Users(0, name, email)
            userAdapter!!.addUser(users)
            userDao?.insert(users)
            nameEdit.setText("")
            cityEdit.setText("")
            Toast.makeText(this@MainActivity, "User inserted", Toast.LENGTH_SHORT).show()
        })

        makeRequest()
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

    private fun makeRequest() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://mocki.io/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api: UserApi = retrofit.create(UserApi::class.java)
        val call: Call<List<Users>> = api.getItems()
        call.enqueue(object : Callback<List<Users>> {
            override fun onResponse(call: Call<List<Users>>, response: Response<List<Users>>) {
                if (response.isSuccessful) {
                    response.body()?.forEach {
                        userAdapter!!.addUser(it)
                        userDao?.insert(it)
                    }
                }
            }

            override fun onFailure(call: Call<List<Users>>, t: Throwable) {
                Log.d("main", "onFailure: " + t.message)
            }
        })
    }
}