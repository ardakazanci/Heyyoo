# Content

It is an exemplary social media application where users motivate each other.

## Architecture components

Ideally, ViewModels shouldn’t know anything about Android. This improves testability, leak safety and modularity. ViewModels have different scopes than activities or fragments. While a ViewModel is alive and running, an activity can be in any of its lifecycle states. Activities and fragments can be destroyed and created again while the ViewModel is unaware.

Passing a reference of the View (activity or fragment) to the ViewModel is a serious risk. Lets assume the ViewModel requests data from the network and the data comes back some time later. At that moment, the View reference might be destroyed or might be an old activity that is no longer visible, generating a memory leak and, possibly, a crash.

The communication between the different layers follow the above diagram using the reactive paradigm, observing changes on components without need of callbacks avoiding leaks and edge cases related with them.

## Patterns

Repository Pattern - The Repository Pattern is one of the most popular patterns to create an enterprise level application. It restricts us to work directly with the data in the application and creates new layers for database operations, business logic, and the application's UI.
Observer Pattern - The observer pattern is a software design pattern in which an object, called the subject, maintains a list of its dependents, called observers, and notifies them automatically of any state changes, usually by calling one of their methods.


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

NodeJs - Mongoose - MongoDb

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




