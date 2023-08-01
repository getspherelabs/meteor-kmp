<h1 align="center">Meteor KMP</h1></br>

<p align="center">
  <a href="https://github.com/getspherelabs/meteor"><img  alt="Meteor Banner" src="https://github.com/getspherelabs/meteor/blob/main/docs/images/Banner%20-%20%20Meteor.png?raw=true"/></a> <br>
</p>

Meteor is a simple framework that helps you to create application using the MVI architecture. It provides a robust structure for organizing your code and separating concerns. Additionally, Meteor integrates with Kotlin Coroutine that helps you to write asynchronous and concurrent code.

## Features

- Meteor follows the Model-View-Intent (MVI) architecture pattern.
- It provides a unidirectional data flow (UDF), allowing you to handle state changes and propagate them to the UI efficiently.
- Supports Kotlin Coroutines: asynchronous and concurrent code in a more concise and structured way.
- Meteor supports Kotlin Multiplatform, allowing you to share code across multiple platforms.
- Easy to test (integration and unit  tests).
- Additional features like common use cases, state flow, view model and logger.

## Documentation 📖

- [Getting Started](https://getspherelabs.github.io/meteor/guides/getting-started)
- [Core Concepts](https://getspherelabs.github.io/meteor/guides/concepts)
- [Common Components](https://getspherelabs.github.io/meteor/common/vm)
  - [Common ViewModel](https://getspherelabs.github.io/meteor/common/vm)  
  - [Common Flow](https://getspherelabs.github.io/meteor/common/flow)
  - [Common UseCase](https://getspherelabs.github.io/meteor/common/usecase)
- [Testing](https://getspherelabs.github.io/meteor/test/tests)


## Setup

Add the dependency below into your module's `build.gradle.kts` file:

```kt

// It includes viewmodel, stateflow and core components.
implementation("io.github.behzodhalil:meteor-mvi:<latest-version>")
// If only need common use case
implementation("io.github.behzodhalil:meteor-usecase:<latest-version>")
// For testing
testImplementation("io.github.behzodhalil:meteor-test:<latest-version>")
```
### Define the contract


```kt
data class CountState(
    val count: Int = 0
)

sealed interface CountWish {
    object Increase : CountWish
    object Decrease : CountWish
    object Reset : CountWish
    object ZeroValue : CountWish
}

sealed interface CountEffect {
    data class Failure(val message: String) : CountEffect
}
```

`State` represents the current state of your application. `Effects` are a way to handle side effects in Meteor. Side effects can include operations such as making network requests, updating a database, displaying UI messages, or triggering external actions.  A `Wish` in Meteor represents an action or an intention to change the state of the application.

### Create the ViewModel

Define a ViewModel class, such as `MainViewModel`, that extends `CommonViewModel` with the appropriate state, wish, and effect types:

```kt
class MainViewModel : CommonViewModel<CountState, CountWish, CountEffect>() {
      // ....
}
```

Inside your ViewModel, override the store property by creating a `Meteor` store using the `createMeteor` function and providing the necessary configurations:

```kt
    override val store: Store<CountState, CountWish, CountEffect> = createMeteor(
        configs = MeteorConfigs.build {
            initialState = CountState.Empty
            storeName = "MainViewModel"
            reducer = CountReducer
            middleware = CountMiddleware
        }
    )
```

Define properties for effect and state in your ViewModel, which will expose the effect and state as `CommonFlow` and `CommonStateFlow`, respectively:

```kt
val effect: NonNullCommonFlow<CountEffect> = store.effect.asCommonFlow()
val state: NonNullCommonStateFlow<CountState> = store.state.asCommonStateFlow()

```

### Define Reducer and Middleware

Create an object / class , such as `CountReducer`, that implements the `Reducer` interface with the appropriate state, wish, and effect types:

```kt
    object CountReducer : Reducer<CountState, CountWish, CountEffect> {
        override fun reduce(state: CountState, wish: CountWish): Change<CountState, CountEffect> {
            return when (wish) {
                CountWish.Decrease -> {
                    expect { state.copy(count = state.count - 1) }
                }
                CountWish.Increase -> {
                    expect { state.copy(count = state.count + 1) }
                }
                CountWish.Reset -> {
                    expect { state.copy(count = 0) }
                }
                CountWish.ZeroValue -> {
                    effect {
                        CountEffect.Failure("The value is zero")
                    }
                }
            }
        }

    }

```

### Using in Fragment / Compose

```kt

viewModel.state.onEach { state ->
    // Handle the state
}.launchIn(lifecycleScope)

viewModel.effect.onEach { effect ->
    // Handle the effect, such as displaying a toast message or triggering an action
}.launchIn(lifecycleScope)


```
## Inspired by

* [Orbit MVI](https://github.com/orbit-mvi/orbit-mvi)
* [MVIKotlin](https://github.com/arkivanov/MVIKotlin)

## License

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE.md)

This project is licensed under the Apache License, Version 2.0 - see the
[license](LICENSE.md) file for details
