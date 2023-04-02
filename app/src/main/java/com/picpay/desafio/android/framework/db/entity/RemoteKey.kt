package com.picpay.desafio.android.framework.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKey(
    @PrimaryKey
    val id: Int = 0,
    @ColumnInfo(name = "next_offset")
    val nextOffset: Int?)
