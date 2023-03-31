package com.picpay.desafio.android.framework.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.picpay.desafio.android.framework.db.dao.UserDao
import com.picpay.desafio.android.framework.db.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao() : UserDao
}