package com.picpay.desafio.android.presentation.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.picpay.desafio.android.R
import com.picpay.desafio.android.databinding.FragmentUserListBinding
import com.picpay.desafio.android.presentation.common.getGenericAdapterOf
import com.picpay.desafio.android.presentation.users.adapter.UserListItemViewHolder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListFragment : Fragment() {

    private var _binding: FragmentUserListBinding? = null
    private val binding: FragmentUserListBinding get() = _binding!!

    private val viewModel: UserListViewModel by viewModels()

    private val userListAdapters by lazy {
        getGenericAdapterOf {
            UserListItemViewHolder.create(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentUserListBinding.inflate(inflater, container, false).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUsersAdapter()
        initObservers()
    }

    private fun initObservers() {
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->

            binding.flipperUsers.displayedChild = when (uiState) {

                is UserListViewModel.UiState.Loading -> {
                    FLIPPER_CHILD_LOADING
                }
                is UserListViewModel.UiState.Success -> {
                    userListAdapters.submitList(uiState.userList)
                    FLIPPER_CHILD_USERS
                }
                is UserListViewModel.UiState.Error -> {
                    Toast.makeText(context, getString(R.string.error), Toast.LENGTH_SHORT).show()
                    FLIPPER_CHILD_LOADING
                }
            }

        }
        viewModel.getUsers()
    }

    private fun initUsersAdapter() {
        binding.recyclerView.run {
            setHasFixedSize(true)
            adapter = userListAdapters
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val FLIPPER_CHILD_LOADING = 0
        private const val FLIPPER_CHILD_USERS = 1
        private const val FLIPPER_CHILD_ERROR = 2
    }
}