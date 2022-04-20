package com.hp.jetpack.demo.model.database

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hp.jetpack.demo.base.viewmodel.BaseViewModel
import com.hp.jetpack.demo.database.db
import com.hp.jetpack.demo.database.entity.User
import kotlinx.coroutines.launch

class RoomViewModel : BaseViewModel() {
    var addFlag = MutableLiveData<Boolean>()

    var deleteFlag = MutableLiveData<Boolean>()

    var data = MutableLiveData<MutableList<User>>()

    fun addUser(name: String, password: String) {
        viewModelScope.launch {
            runCatching {
                db.userDao().insertUser(User(0,name,password))
                true
            }.onSuccess {
                addFlag.postValue(it)
            }.onFailure {
                it.printStackTrace()
                addFlag.postValue(false)
            }
        }
    }

    fun getAll() {
        viewModelScope.launch {
            runCatching {
                db.userDao().getAll()
            }.onSuccess {
                data.postValue(it.toMutableList())
            }.onFailure {
                it.printStackTrace()
            }
        }
    }

    fun deleteUser(user: User) {
        runCatching {
            db.userDao().delete(user)
            true
        }.onSuccess {
            deleteFlag.postValue(it)
        }.onFailure {
            it.printStackTrace()
            deleteFlag.postValue(false)
        }
    }
}