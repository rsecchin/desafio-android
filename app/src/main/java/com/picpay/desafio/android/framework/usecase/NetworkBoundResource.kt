package com.picpay.desafio.android.framework.usecase

import kotlinx.coroutines.flow.*

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {
    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(ResultStatus.Loading)

        try {
            saveFetchResult(fetch())
            query().map { ResultStatus.Success(it) }
        } catch (throwable: Throwable) {
            query().map { ResultStatus.Error(throwable) }
        }
    } else {
        query().map { ResultStatus.Success(it) }
    }

    emitAll(flow)
}
