package com.picpay.desafio.android.presentation.users.adapter

import com.picpay.desafio.android.presentation.common.ListItem

data class UserItem(
    val img: String,
    val name: String,
    val id: Int,
    val username: String,
    override val key: Long = id.toLong(),
): ListItem