package fake

import io.spherelabs.meteorusecase.SuspendableUseCase
import kotlinx.coroutines.CoroutineDispatcher

class FakeSuspendableUseCase(
    private val dispatcher: CoroutineDispatcher
) : SuspendableUseCase<Unit, String>(dispatcher) {

    override suspend fun execute(params: Unit): Result<String> {
        return Result.success("10")
    }
}
