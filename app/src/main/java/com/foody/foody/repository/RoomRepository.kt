package com.foody.foody.repository

import com.foody.foody.db.AppDao
import com.foody.foody.model.User
import javax.inject.Inject

class RoomRepository @Inject constructor(private val dao: AppDao) {
    suspend fun insert(user: User) {
        dao.insert(user)
    }
}