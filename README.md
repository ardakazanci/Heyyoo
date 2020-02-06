# A sample modern Kotlin application - Documentation still under maintenance
[![CircleCI](https://circleci.com/gh/ardakazanci/Sample-Social-Media-App-MVVM.svg?style=svg)](https://circleci.com/gh/ardakazanci/Sample-Social-Media-App-MVVM)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/8da4dc07cf3f426caa9397804792277f)](https://www.codacy.com/manual/ardakazanci/Sample-Social-Media-App-MVVM?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=ardakazanci/Sample-Social-Media-App-MVVM&amp;utm_campaign=Badge_Grade)
[![codebeat badge](https://codebeat.co/badges/a7cc6858-523a-41a0-b288-aabe6ef85431)](https://codebeat.co/projects/github-com-ardakazanci-sample-social-media-app-mvvm-master)
[![CodeFactor](https://www.codefactor.io/repository/github/ardakazanci/sample-social-media-app-mvvm/badge)](https://www.codefactor.io/repository/github/ardakazanci/sample-social-media-app-mvvm)
[![Kotlin Version](https://img.shields.io/badge/kotlin-1.3.61-blue.svg)](https://kotlinlang.org)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)
<a href="https://opensource.org/licenses/MIT"><img alt="License" src="https://img.shields.io/badge/License-MIT-blue.svg"/></a>

# Content

It is an exemplary social media application where users motivate each other.

## Architecture components

Ideally, ViewModels shouldn’t know anything about Android. This improves testability, leak safety and modularity. ViewModels have different scopes than activities or fragments. While a ViewModel is alive and running, an activity can be in any of its lifecycle states. Activities and fragments can be destroyed and created again while the ViewModel is unaware.

Passing a reference of the View (activity or fragment) to the ViewModel is a serious risk. Lets assume the ViewModel requests data from the network and the data comes back some time later. At that moment, the View reference might be destroyed or might be an old activity that is no longer visible, generating a memory leak and, possibly, a crash.

The communication between the different layers follow the above diagram using the reactive paradigm, observing changes on components without need of callbacks avoiding leaks and edge cases related with them.

## Patterns

- Repository Pattern - The Repository Pattern is one of the most popular patterns to create an enterprise level application. It restricts us to work directly with the data in the application and creates new layers for database operations, business logic, and the application's UI.
- Observer Pattern - The observer pattern is a software design pattern in which an object, called the subject, maintains a list of its dependents, called observers, and notifies them automatically of any state changes, usually by calling one of their methods.


## Usage

Values ​​in Constants.kt file must be filled.


```bash
        const val API_URL = "https://sample-node-social-app.herokuapp.com/"
        const val PREF_USER_TOKEN_VALUE = "PREF_USER_TOKEN_VALUE"
        const val PREF_USER_ID_VALUE = "PREF_USER_ID_VALUE"
        const val PREF_USER_TOKEN = "user_token_code.xml" 
        const val ALGOLIA_APPLICATION_ID = "xxxx"
        const val ALGOLIA_SEARCH_ONLY_API_KEY = "xxxxx"
        const val ALGOLIA_ADMIN_API_KEY = "xxxxx"
        const val ALGOLIA_INDEX_NAME = "user-list"
```

## Features

- Followed - Following System
- User Search System
- Content Share(Location,Text) System
- User Profile System
- Register - Login System
- ...
## BackEnd

- NodeJs 
- Mongoose
- MongoDb

## Libraries

- Retrofit : type-safe HTTP client.
- Coroutines :  managing background threads with simplified code and reducing needs for callbacks.
- Room Library
- Lifecycle : perform actions in response to a change in the lifecycle status of another component, such as activities and fragments.
- ViewModel : designed to store and manage UI-related data in a lifecycle conscious way. The ViewModel class allows data to survive configuration changes such as screen rotations.
- LiveData : lifecycle-aware, meaning it respects the lifecycle of other app components, such as activities, fragments, or services.
- Data Binding - allows you to bind UI components in your layouts to data sources in your app using a declarative format rather than programmatically.
- Navigation
- Gson : makes it easy to parse JSON into Kotlin objects.
- Glide : image loading library for Android.
- CircleImageView
- EasyValidation
- Toasty
- Dexter
- RxImagePicker
- Secure-Preferences
- Algolia - instantsearch
- Paging
- Splashy
- Locgetter
- ReactiveNetwork
- Lottie
- ...

## Resources

### Projects

This is project is a sample, to inspire you and should handle most of the common cases, but obviously not all. If you need to take a look at additional resources to find solutions for your project, visit these interesting projects:

-   [sunflower](https://github.com/android/sunflower) (by [android](https://github.com/android)) - a gardening app illustrating Android development best practices with Android Jetpack.
-   [architecture-components-samples](https://github.com/android/architecture-components-samples) (by [android](https://github.com/android)) - collection of samples for Android Architecture Components.
-   [architecture-sample](https://github.com/android/architecture-samples) (by [android](https://github.com/android)) - collection of samples to discuss and showcase different architectural tools and patterns for Android apps.

### Libraries

The open-source community create and maintains tons of awesome libraries making your job more easy, giving the opportunity to use them in your developments. Here are a very important collection of them:

-   [awesome-android-ui](https://github.com/wasabeef/awesome-android-ui) - collection list of awesome Android UI/UX libraries.
-   [awesome-android-libraries](https://github.com/KotlinBy/awesome-kotlin#android-libraries) - collection of awesome Kotlin related stuff.
-   [android-arsenal](https://android-arsenal.com/) - android developer portal with tools, libraries, and apps.

### Best practices

Avoid reinventing the wheel by following these guidelines:

-   [Google best practices](https://developer.android.com/distribute/best-practices)
-   [Android development best practices](https://github.com/futurice/android-best-practices)

### Codelabs

Google Developers Codelabs provide a guided, tutorial, hands-on coding experience. Most codelabs will step you through the process of building a small application, or adding a new feature to an existing application. They cover a wide range of android concepts to learn and practice:

-   [Android Developer Fundamentals](https://developer.android.com/courses/fundamentals-training/toc-v2)
-   [Android Developer Codelabs](https://codelabs.developers.google.com/?cat=Android)


## Thank You

Thank You Sanchit Sharma - [Dribble](https://dribbble.com/shots/6612479-Social-Katchup-F)

Logo - [FlatIcon](https://www.flaticon.com/free-icon/love_1029183?term=Like&page=1&position=4)

Thank Yoy @nuhkoca  for guiding projects and documentation brief descriptions.
 

## Images

![](https://i.hizliresim.com/zyvVVj.png)
![](https://i.hizliresim.com/yj8llj.png)
![](https://i.hizliresim.com/JWr44Q.png)
![](https://i.hizliresim.com/OanZZD.png)
![](https://i.hizliresim.com/GGQkkZ.png)
![](https://i.hizliresim.com/3gBaaM.png)

```
Copyright 2019 Arda Kazancı

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```




