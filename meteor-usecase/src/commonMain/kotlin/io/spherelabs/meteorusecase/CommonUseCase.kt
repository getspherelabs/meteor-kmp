package io.spherelabs.meteorusecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

public abstract class CommonUseCase<Param, ReturnType>(
    private val dispatcher: CoroutineDispatcher
) {
    public abstract fun execute(param: Param): Flow<Result<ReturnType>>

    public operator fun invoke(param: Param): Flow<Result<ReturnType>> {
        return execute(param).catch { e ->
            emit(Result.failure(e))
        }.flowOn(dispatcher)
    }
}
