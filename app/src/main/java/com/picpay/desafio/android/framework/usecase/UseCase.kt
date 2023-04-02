package com.picpay.desafio.android.framework.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

abstract class PagingUseCase<in P, R : Any> {

    operator fun invoke(params: P): Flow<PagingData<R>> = createFlowObservable(params)

    protected abstract fun createFlowObservable(params: P): Flow<PagingData<R>>
}