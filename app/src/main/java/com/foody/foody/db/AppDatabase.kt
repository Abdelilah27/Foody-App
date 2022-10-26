package com.foody.foody.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.foody.foody.di.ApplicationScope
import com.foody.foody.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appDao(): AppDao

    class Callback @Inject() constructor(
        private val database: Provider<AppDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val dao = database.get().appDao()
            applicationScope.launch {
                dao.insert(
                    User(
                        name = "Abdelilah", email = "Abdelilah@gmail.com",
                        password =
                        "abdel123",
                    )
                )
                dao.insert(
                    User(
                        name = "Kamal", email = "Kamal@gmail.com",
                        password =
                        "kamal123",
                    )
                )
            }
        }
    }


}