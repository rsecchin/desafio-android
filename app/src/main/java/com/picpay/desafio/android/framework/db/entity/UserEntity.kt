package com.picpay.desafio.android.framework.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.picpay.desafio.android.presentation.users.User

@Entity(tableName = "users")
data class UserEntity (
    @PrimaryKey(autoGenerate = true)
    val autoId: Int = 0,
    @ColumnInfo(name = "user_img") val img: String,
    @ColumnInfo(name = "user_name") val name: String,
    @ColumnInfo(name = "user_id") val id: Int,
    @ColumnInfo(name = "user_username") val username: String
)

fun List<UserEntity>.toUsersModel() = map { User(it.img, it.name, it.id, it.username) }