# News app

Code written from **Sungho Moon**

This repository contains a news article sample app and data fetched from <br/>`https://bruce-v2-mob.fairfaxmedia.com.au/`<br/>
The app populates list of news on `RecyclerView` and can redirect to news website on clicking one of news items from the app.

## Build
* IDE: Android Studio Bumblebee 2021.1.1 Patch2 (TESTED)
* IDE: Android Studio Bumblebee 2021.1.1 Canary1 (TESTED)
* IDE: Android Studio ArcticFox 2020.3.1 Patch3 (TESTED)
* Code: Kotlin
* Gradle JDK (If this step required) : Version 11 (Preference -> Build, Execution, Deployment -> Build Tools -> Gradle -> Select Gradle JDK version 11)
* SDK version and dependencies are defined at `gradle.properties`
* Test devices: Pixel 3 XL API 28, Galaxy S10

## Tech Stack Summary
* ViewModel, MVVM Pattern
* Android View Binding
* Coroutine
* Hilt
* Espresso + Hilt
* Jetpack Navigation


## Architecture
* The news app has applied `Clean Architecture`, having 3 different modules for separation of concerns.
* Each modules has unit tests and UI test deployed in `UI module`

```
[News app] -- [UI] ----- Code
               |      |
               |      -- Unit Test
               |      |
               |      -- UI test
               |
          [business] --- Code
               |      |
               |      -- Unit Test
               |
            [Data] ----- Code
                      |
                      -- Unit Test
```

### UI module
* As for an architecture, `MVVM` pattern applied to this application.
* At the creation of `ViewModel`, `ViewState` will be `Loading` and API will be called.
* Once data fetched, `ViewState` will be updated to `Success` with valid data.
* If error happened, `ViewState` will be updated to `Error` with a message.

### Business module
* `NewManager` will call a repository (in data module) to fetch values. Once fetched, data object will be mapped into domain object for later use.
* If mapping to domain object completed, it will return success with value. Otherwise, will return error cases.

### Data module
* Repository injected in singleton scope. API Response will be fetched from this repository. It will return success with fetched values, or return error with an error code and a message.

## Feature Implementation
* Display latest article first in the list.
* One asset has a smallest image, which is calculated as `width x height` and finding minimal one from related images.
* Tapping a story will redirect to next page for url load.
* Image load library used (Glide) for processing asynchronously and cached.
* Applied `ViewModel` and can avoid screen rotation issue.
* Pull to refresh implemented.
* Unit test implemented. (See module diagram above for finding location of unit tests)
* UI test implemented. (See module diagram above for finding location of UI tests)
* Error handling of Webview
* Error handling of main page list loading. (Network issue etc.)


## Used library
### UI module
* `Coroutine`: Business logic running background and handling view resources on completion.
* `Hilt`: Dependency Injection for business manager, mapper and repository etc.
* `Jetpack Navigation`: Visual UI flow supported. This is alternative of fragment transactions.
* `Pull To Refresh`: Pull to reload page and refresh the page.
* `Glide`: Image loading library asynchronously.
* `Coroutine Test`: Providing unit test rule for coroutine.
* `KotlinMock2`: Unit test for Kotlin.
* `AssertJ`: Simple matcher provided.
* `Hilt Test`: UI test with Hilt supported.

### Business module
* `Hilt`: Dependency Injection for business layer.
* `AssertJ`: Simple matcher provided.
* `KotlinMock2`: Unit test for Kotlin.
* `Coroutine Test`: Providing unit test rule for coroutine.

### Data module
* `Retrofit`: API handling and fetching response from endpoint.
* `Retrofit GSON`: Serializing field name for response.
* `Coroutine`: For data layer.
* `Hilt`: Dependency Injection for data layer.
* `AssertJ`: Simple matcher provided.
* `KotlinMock2`: Unit test for Kotlin.
* `OkHttpVersion`: Only for unit test for mock response.
* `Retrofit Coroutine`: Coroutine adapter for Retrofit
