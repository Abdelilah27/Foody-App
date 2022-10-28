package com.foody.foody.db

import androidx.room.*
import com.foody.foody.model.User

@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM USER_TABLE WHERE email=:email")
    suspend fun findUserByEmail(email: String): List<User>?

}