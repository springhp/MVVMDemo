package com.hp.jetpack.demo.database

import androidx.room.Room
import com.blankj.utilcode.util.Utils
import com.hp.jetpack.demo.network.ApiService
import com.hp.jetpack.demo.network.NetworkApi


val db: MyDataBase by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    DataBaseManager.INSTANCE.getDataBase()
}

class DataBaseManager {

    companion object {
        val INSTANCE: DataBaseManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { DataBaseManager() }
    }

    private var db:MyDataBase = Room.databaseBuilder(
        Utils.getApp(),
        MyDataBase::class.java,
        MyDataBase.DATA_BASE_NAME
    ).allowMainThreadQueries()
        .build()

    fun getDataBase(): MyDataBase {
        return db
    }
}