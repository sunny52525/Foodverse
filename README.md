This is a Kotlin Multiplatform project targeting Android, iOS, Web, Desktop.
This repository I created to test compose multiplatform and should not be taken reference for any compose multiplatform architecture design

<img width="450" alt="image" src="https://github.com/sunny52525/Foodverse/assets/30768018/6303d41e-447b-4c27-9109-2e6a9020d882">

<img width="1728" alt="image" src="https://github.com/sunny52525/Foodverse/assets/30768018/46165488-73b0-43e2-a0b7-6ffd8dea6a48">
<img width="411" alt="image" src="https://github.com/sunny52525/Foodverse/assets/30768018/b3611d79-3492-4602-860e-cda041fdabe3">
<img width="1728" alt="image" src="https://github.com/sunny52525/Foodverse/assets/30768018/02803c1d-5166-469f-833f-90b5cfb9f5bd">


* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html),
[Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform/#compose-multiplatform),
[Kotlin/Wasm](https://kotl.in/wasm/)…

**Note:** Compose/Web is Experimental and may be changed at any time. Use it only for evaluation purposes.
We would appreciate your feedback on Compose/Web and Kotlin/Wasm in the public Slack channel [#compose-web](https://slack-chats.kotlinlang.org/c/compose-web).
If you face any issues, please report them on [GitHub](https://github.com/JetBrains/compose-multiplatform/issues).

You can open the web application by running the `:composeApp:wasmJsBrowserDevelopmentRun` Gradle task.
