package fake

import io.spherelabs.meteor.dsl.meteor
import io.spherelabs.meteor.store.Store
import io.spherelabs.meteor.viewmodel.CommonViewModel
import io.spherelabs.meteorviewmodel.commonflow.NonNullCommonFlow
import io.spherelabs.meteorviewmodel.commonflow.NonNullCommonStateFlow
import io.spherelabs.meteorviewmodel.commonflow.asCommonFlow
import io.spherelabs.meteorviewmodel.commonflow.asCommonStateFlow

class CountDslViewModel : CommonViewModel<FakeCountState, FakeCountWish, FakeCountEffect>() {

    override val store: Store<FakeCountState, FakeCountWish, FakeCountEffect> = meteor {
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
            on { _, _ -> }
        }
    }

    val effect: NonNullCommonFlow<FakeCountEffect> = store.effect.asCommonFlow()
    val state: NonNullCommonStateFlow<FakeCountState> = store.state.asCommonStateFlow()
}
