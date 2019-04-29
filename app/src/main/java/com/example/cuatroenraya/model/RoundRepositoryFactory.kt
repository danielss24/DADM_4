package com.example.cuatroenraya.model

import android.content.Context
import com.example.cuatroenraya.database.DataBase


object RoundRepositoryFactory {
    private val LOCAL = true
    fun createRepository(context: Context): RoundRepository? {
        val repository: RoundRepository
        repository = if (LOCAL) DataBase(context) else DataBase(context)
        try {
            repository.open()
        } catch (e: Exception) {
            return null
        }
        return repository
    }
}