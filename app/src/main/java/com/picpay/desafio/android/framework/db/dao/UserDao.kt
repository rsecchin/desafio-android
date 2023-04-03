package com.picpay.desafio.android.framework.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.picpay.desafio.android.framework.db.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(cars: List<UserEntity>)

    @Query("SELECT * FROM users")
    fun getAllUsers(): PagingSource<Int, UserEntity>

    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()
}