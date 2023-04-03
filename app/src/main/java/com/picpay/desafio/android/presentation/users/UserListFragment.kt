package com.picpay.desafio.android.presentation.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.picpay.desafio.android.databinding.FragmentUserListBinding
import com.picpay.desafio.android.presentation.users.adapter.UsersAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserListFragment : Fragment() {

    private var _binding: FragmentUserListBinding? = null
    private val binding: FragmentUserListBinding get() = _binding!!

    private val viewModel: UserListViewModel by viewModels()

    private val userListAdapters by lazy {
        UsersAdapter()
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
        observeInitialLoadState()

        viewModel.state.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is UserListViewModel.UiState.SearchResult -> {
                    userListAdapters.submitData(viewLifecycleOwner.lifecycle, uiState.data)
                }
            }
        }

        viewModel.getUsers()
    }

    private fun initUsersAdapter() {
        postponeEnterTransition()
        with(binding.recyclerUsers) {
            setHasFixedSize(true)
            adapter = userListAdapters
        }
    }

    private fun observeInitialLoadState() {

        lifecycleScope.launch {
            userListAdapters.loadStateFlow.collectLatest { loadState ->

                binding.flipperUsers.displayedChild = when {
                    loadState.mediator?.refresh is LoadState.Loading -> {
                        FLIPPER_CHILD_LOADING
                    }
                    loadState.mediator?.refresh is LoadState.Error
                            && userListAdapters.itemCount == 0 -> {
                        FLIPPER_CHILD_ERROR
                    }
                    loadState.source.refresh is LoadState.NotLoading
                            || loadState.mediator?.refresh is LoadState.NotLoading -> {
                        FLIPPER_CHILD_USERS
                    }
                    else -> {
                        FLIPPER_CHILD_USERS
                    }
                }
            }
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