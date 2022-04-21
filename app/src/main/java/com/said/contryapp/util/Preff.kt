package com.said.contryapp.util


import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class Preff {

    companion object {
        private var sharedPreferences: SharedPreferences? = null

        @Volatile
        private var instance: Preff? = null

        private val lock = Any()

        operator fun invoke(context: Context): Preff = instance ?: synchronized(lock) {
            instance ?: makeObject(context = context).also {
                instance = it
            }
        }

        private fun makeObject(context: Context): Preff {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return Preff()
        }
    }

    fun saveTime(time : Long){
        sharedPreferences?.edit(commit = true){
            putLong("time", time)
        }
    }

    fun getTime() = sharedPreferences?.getLong("time", 0)
}