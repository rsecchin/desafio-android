package com.picpay.desafio.android.presentation.common

interface ListItem {

    val key: Long

    fun areItemsTheSame(other: ListItem) = this.key == other.key

    fun areContentsTheSame(other: ListItem) = this == other
}