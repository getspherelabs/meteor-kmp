package fake

import io.spherelabs.meteor.dsl.meteor
import io.spherelabs.meteor.store.Store
import io.spherelabs.meteor.viewmodel.CommonViewModel

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
            on { _, _, _ -> }
        }
    }
}
