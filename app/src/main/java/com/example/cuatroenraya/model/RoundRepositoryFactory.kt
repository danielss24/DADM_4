package com.example.cuatroenraya.model

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.cuatroenraya.database.DataBase
import com.example.cuatroenraya.firebase.FBDataBase


object RoundRepositoryFactory {
    fun createRepository(context: Context): RoundRepository? {
        val repository: RoundRepository
        var OnlineMode = PreferenceManager.getDefaultSharedPreferences(context).getBoolean("OnlineMode",false)
        repository = if (OnlineMode == true) FBDataBase() else DataBase(context)
        try {
            repository.open()
        } catch (e: Exception) {
            return null
        }
        return repository
    }
}