# Trending-Movies

[![Android Build & Unit Test](https://github.com/RemonShehata/Trending-Movies/actions/workflows/android_build&unit_test.yml/badge.svg)](https://github.com/RemonShehata/Trending-Movies/actions/workflows/android_build&unit_test.yml)

<p align="center">
Trending Movies app is a small demo application to demonstrate modern Android application tech-stacks with MVVM architecture, with emphasize on Unit & UI testing.
</p>

## Tech stack & Open-source libraries
- Minimum SDK level 28
- [Kotlin](https://kotlinlang.org/)
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines)
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/)
- [StateFlow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-state-flow/index.html)
- [Koin](https://insert-koin.io) for dependency injection.
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) is a dependency injection library for Android, it can be viewed in a different branch [here](https://github.com/AhmedVargos/Valorant-Agents/blob/using_hilt_for_di/README.md)
- [JetPack](https://developer.android.com/jetpack)
    - LiveData - Notify domain layer data to views.
    - Lifecycle - Dispose of observing data when lifecycle state changes.
    - Fragment-ktx - A set of Kotlin extensions that helps with fragment lifecycle.
    - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Allows you to more easily write code that interacts with views
    - ViewModel - UI related data holder, lifecycle aware.
    - Room Persistence - construct a database using the abstract layer.
- Architecture
    - Multi-module design for the app.
    - MVVM Architecture (View - ViewModel - Model)
    - Repository pattern.
    - Clean Architecture approach.
- [Gradle Groovy](https://docs.gradle.org/current/userguide/groovy_plugin.html)
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - Construct the REST APIs.
- [Moshi](https://github.com/square/moshi) - A Modern JSON library for Android and Java.
- [Glide](https://github.com/bumptech/glide) - For Loading images from Urls.
- [Detekt](https://github.com/detekt/detekt)- A static code analysis tool for the Kotlin programming language. Visit the project website for installation guides, rule descriptions, configuration options and more.
- [Material-Components](https://github.com/material-components/material-components-android) - Material design components.
- [CI/CD with github Actions](https://docs.github.com/en/actions) - Automate, customize, and execute your software development workflows.

## Handled scenarios:
- This application is built with Database first approach in mind.
    - We get the data from the remote server then we save it in the Database
    - Then we listen for database updates, whenever there is an update in the Database, the UI is updated.
    - This approach allows us to have a single source of truth for data while having a caching mechanism.
