package com.hp.jetpack.demo.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.hp.jetpack.demo.database.entity.User

@Dao
interface UserDao {
    @Query("select * from tb_user")
    fun getAll(): List<User>

    @Query("select * from tb_user where id = (:id)")
    fun getUserById(id: Int): User

    @Query("select * from tb_user where name = :name and password = :password limit 1")
    fun findByNameAndPassword(name: String, password: String): User

    @Insert
    fun insertUser(user: User)

    @Delete
    fun delete(user: User)
}