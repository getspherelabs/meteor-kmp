package io.spherelabs.meteorusecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

public abstract class SuspendableUseCase<Param, ReturnType>(
    private val dispatcher: CoroutineDispatcher
) {
    public abstract suspend fun execute(params: Param): Result<ReturnType>

    public suspend operator fun invoke(params: Param): Result<ReturnType> {
        return try {
            withContext(dispatcher) {
                execute(params)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
