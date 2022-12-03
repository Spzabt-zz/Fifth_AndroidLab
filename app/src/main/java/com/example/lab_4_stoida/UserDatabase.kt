package com.example.lab_4_stoida

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lab_4_stoida.UserDatabase

@Database(entities = [Users::class], version = 2)
abstract class UserDatabase : RoomDatabase() {
    abstract val dao: UserDao?

    companion object {
        var INSTANCE: UserDatabase? = null
        fun getInstance(context: Context?): UserDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context!!, UserDatabase::class.java, "UserDatabase")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE
        }
    }
}