package com.said.contryapp.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.said.contryapp.model.Country

@Database(entities = [Country::class], version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun countryDao() : CountryDao

    companion object{
        @Volatile
        private var instance : MyDatabase? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: makeDatabase(context = context).also {
                instance = it
            }
        }

        private fun makeDatabase(context : Context) = Room.databaseBuilder(
            context.applicationContext, MyDatabase::class.java, "mydb"
        ).build()
    }
}