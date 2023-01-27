package com.example.database.views

import android.content.Context
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.Room
import com.example.database.entities.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class MainDatabase : RoomDatabase() {
    abstract fun userDao() : DAO

    companion object
    {
        private var instance : MainDatabase? = null

        fun getInstance(context : Context) : MainDatabase
        {
            if (instance == null)
            {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDatabase::class.java, "mToiletDatabase"
                ).allowMainThreadQueries().build()
            }

            return instance as MainDatabase
        }
    }
}