# Custom Camera Filters

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger) ![](https://img.shields.io/badge/dynamic/xml?url=https://kotlinlang.org/&label=Kotlin&query=/&color=3399cc&prefix=v1.5.20&logo=kotlin) ![](https://img.shields.io/badge/dynamic/xml?url=https://kotlinlang.org/&label=API&query=/&color=5b39c6&prefix=22) ![](https://img.shields.io/badge/dynamic/xml?url=https://kotlinlang.org/&label=code%20quality&query=/&color=4BB543&prefix=A&logo=Codacy)

![Alt text](camera_filter.gif) / ![](camera_filter.gif)

# Getting Started
Custom camera filter can capture image from camera and also pick image from gallery. After capturing image user can add filters from filter library. Custom camera filter has 15 filters. User can save image after applying filters.

# Prerequisites
### Android Studio IDE setup

For development, it requires `Android studio version 3.5` or above. The latest version can be downloaded from [here](https://developer.android.com/studio/).

### Libraries Used

* [Foundation][0] - Components for core system capabilities, Kotlin extensions, and support for multidex and automated testing.
  * [AppCompat][1] - Degrade gracefully on older versions of Android.
  * [Android KTX][2] - Write more concise, idiomatic Kotlin code.
  * [Test][4] - An Android testing framework for unit and runtime UI tests.
* [Architecture][10] - A collection of libraries that help you design robust, testable, and maintainable apps. Start with classes for managing your UI component lifecycle and handling data persistence.
  * [Lifecycles][12] - Create a UI that automatically responds to lifecycle events.
  * [LiveData][13] - Build data objects that notify views when the underlying database changes.

* [UI][30] - Details on why and how to use UI Components in your apps - together or separate
  * [Animations & Transitions][31] - Move widgets and transition between screens.
  * [Layout][35] - Lay out widgets using different algorithms.
* Third party
  * [Kotlin Coroutines][91] - for managing background threads with simplified code and reducing needs for callbacks
  * [Card view][25] - It is a FrameLayout with a rounded corner background and shadow.
  * [Recycler view][26] - It is a new ViewGroup that is prepared to render any adapter-based view in a similar way. It is supossed to be the successor of ListView and GridView
  * [Glide][93] - It is used to load images smoothly in our app
  * [Filter Library][27] - It provides filter to apply on image


[0]: https://developer.android.com/jetpack/components
[1]: https://developer.android.com/topic/libraries/support-library/packages#v7-appcompat
[2]: https://developer.android.com/kotlin/ktx
[4]: https://developer.android.com/training/testing/
[10]: https://developer.android.com/jetpack/arch/
[12]: https://developer.android.com/topic/libraries/architecture/lifecycle
[13]: https://developer.android.com/topic/libraries/architecture/livedata
[17]: https://developer.android.com/topic/libraries/architecture/viewmodel
[18]: https://developer.android.com/topic/libraries/architecture/workmanager
[19]: https://developer.android.com/training/testing/espresso
[30]: https://developer.android.com/guide/topics/ui
[31]: https://developer.android.com/training/animation/
[35]: https://developer.android.com/guide/topics/ui/declaring-layout
[91]: https://kotlinlang.org/docs/reference/coroutines-overview.html
[93]: https://bumptech.github.io/glide/
[25]: https://developer.android.com/jetpack/androidx/releases/cardview
[26]: https://developer.android.com/reference/android/support/v7/widget/RecyclerView
[27]: https://github.com/hgayan7/FilterLibrary


### Static Code Analysis Tool
In this project, we have used eight different type of Static Code Analysis tools as listed below

    1.  Detekt
    2.  Spotless

### Permissions used

##### Requested only when needed
* `CAMERA`: requires for accessing the camera.(default disabled).

## How to setup project?

1. Clone this repository in a location of your choice, like your projects folder, using this command  `"git clone https://github.com/SimformSolutionsPvtLtd/SSCustomCameraControl.git"` over terminal.

2. Start Android Studio and go File/Open select project folder.

3. It will take some time to build and download Gradle dependencies.

4. Once completed, there will be a message that says `"BUILD SUCCESSFUL"`.

5. Yup! You are all set now. To run just hit ► the (run) button.  🚀

### How to use?

There are two different `productflavors` available for running the project either in `development`  environment.

1. **Development** - Set the active Build Variant as `"developmentDebug"` to run the project in the Development environment.


