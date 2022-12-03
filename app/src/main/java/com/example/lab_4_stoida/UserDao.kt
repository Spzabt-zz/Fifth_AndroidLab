package com.example.lab_4_stoida

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Insert
    fun insert(users: Users?)

    @Update
    fun update(users: Users?)

    @Query("delete from Users where id=:id")
    fun delete(id: Int)

    @Query("select * from Users")
    fun getAllUser(): List<Users>
}