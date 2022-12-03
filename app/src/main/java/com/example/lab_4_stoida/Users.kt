package com.example.lab_4_stoida

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Users(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var name: String,
    var email: String
) : Serializable