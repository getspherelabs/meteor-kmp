package fake

import io.spherelabs.meteorusecase.CommonUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCommonUseCase(
    private val dispatcher: CoroutineDispatcher
) : CommonUseCase<TestStatus, String>(dispatcher) {

    override fun execute(param: TestStatus): Flow<Result<String>> {
        return flow {
            when (param) {
                TestStatus.SUCCESS -> {
                    emit(Result.success("10"))
                }
                TestStatus.FAILURE -> {
                    emit(Result.failure(NullPointerException()))
                }
            }
        }
    }
}

enum class TestStatus {
    SUCCESS,
    FAILURE
}
