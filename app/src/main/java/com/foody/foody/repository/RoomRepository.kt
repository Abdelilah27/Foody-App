package com.foody.foody.repository

import com.foody.foody.db.AppDao
import com.foody.foody.model.User
import javax.inject.Inject

class RoomRepository @Inject constructor(private val dao: AppDao) {
    suspend fun insert(user: User) {
        dao.insert(user)
    }

    suspend fun findUserByEmail(email: String): List<User>? {
        return dao.findUserByEmail(email)
    }

    suspend fun findUserByEmailAndPass(email: String, password: String): List<User>? {
        return dao.findUserByEmailAndPass(email, password)
    }
}