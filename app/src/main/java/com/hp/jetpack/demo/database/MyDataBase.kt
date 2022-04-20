package com.hp.jetpack.demo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hp.jetpack.demo.database.dao.UserDao
import com.hp.jetpack.demo.database.entity.User

// 修改版本号，非常麻烦
@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class MyDataBase : RoomDatabase() {
    companion object {
        const val DATA_BASE_NAME = "db_test";
    }

    abstract fun userDao(): UserDao
}