package com.picpay.desafio.android.presentation.users.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.picpay.desafio.android.R
import com.picpay.desafio.android.databinding.ListItemUserBinding
import com.picpay.desafio.android.presentation.common.GenericViewHolder
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class UserListItemViewHolder(
    itemBinding: ListItemUserBinding
) : GenericViewHolder<UserItem>(itemBinding) {

    private val name = itemBinding.name
    private val username = itemBinding.username
    private val progressBar = itemBinding.progressBar
    private val picture = itemBinding.picture

    override fun bind(data: UserItem) {
        name.text = data.name
        username.text = data.username
        progressBar.visibility = View.VISIBLE

        Picasso.get()
            .load(data.img)
            .error(R.drawable.ic_round_account_circle)
            .into(picture, object : Callback {
                override fun onSuccess() {
                    progressBar.visibility = View.GONE
                }

                override fun onError(e: Exception?) {
                    progressBar.visibility = View.GONE
                }
            })
    }

    companion object {
        fun create(parent: ViewGroup): UserListItemViewHolder {
            val itemBinding = ListItemUserBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

            return UserListItemViewHolder(itemBinding)
        }
    }
}