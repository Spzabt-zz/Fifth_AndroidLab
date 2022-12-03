package com.example.lab_4_stoida

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab_4_stoida.UserAdapter.MyViewHolder

class UserAdapter(listener: AdapterListener) :
    RecyclerView.Adapter<MyViewHolder>() {
    private val usersList: MutableList<Users>
    private val adapterListener: AdapterListener

    init {
        usersList = ArrayList()
        adapterListener = listener
    }

    fun addUser(users: Users) {
        usersList.add(users)
        notifyDataSetChanged()
    }

    fun removeUser(position: Int) {
        usersList.removeAt(position)
        notifyDataSetChanged()
    }

    fun clearData() {
        usersList.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        val users = usersList[position]
        holder.name.text = users.name
        holder.email.text = users.email
        holder.update.setOnClickListener { adapterListener.OnUpdate(users) }
        holder.delete.setOnClickListener { adapterListener.OnDelete(users.id, position) }
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView
        val email: TextView
        val update: ImageView
        val delete: ImageView

        init {
            name = itemView.findViewById(R.id.name)
            email = itemView.findViewById(R.id.email)
            update = itemView.findViewById(R.id.update)
            delete = itemView.findViewById(R.id.delete)
        }
    }
}