package fake

import io.spherelabs.meteor.dsl.meteor
import io.spherelabs.meteor.store.Store
import io.spherelabs.meteor.viewmodel.CommonViewModel
import io.spherelabs.meteorviewmodel.flow.NonNullCommonFlow
import io.spherelabs.meteorviewmodel.flow.NotNullCommonStateFlow
import io.spherelabs.meteorviewmodel.flow.asCommonFlow

class CountDslViewModel : CommonViewModel<FakeCountState, FakeCountWish, FakeCountEffect>() {

    override val host: Store<FakeCountState, FakeCountWish, FakeCountEffect> = meteor {
        config {
            initialState = FakeCountState()
        }

        reducer {
            on<FakeCountWish.Decrease> {
                transition(
                    action = {
                        copy(count = count - 1)
                    }
                )
            }
            on<FakeCountWish.Increase> {
                transition(
                    action = {
                        copy(count = count + 1)
                    }
                )
            }
        }

        middleware {
            on { fakeWish, suspendFunction1 -> }
        }
    }

    val effect: NonNullCommonFlow<FakeCountEffect> = host.effect.asCommonFlow()
    val state: NotNullCommonStateFlow<FakeCountState> = host.state.asCommonFlow()
}
