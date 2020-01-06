# 基于 MVP 的 Android 组件化开发框架实践

## 一、背景

当我们的项目变得越来越大，代码变得越来越臃肿，耦合会越来越多，编译速度越来越慢，开发效率也会变得越来越低，怎么办？这个时候我们就需要对旧项目进行重构，即是模块的拆分，官方的说法就是组件化。

## 二、简介

那什么是组件化呢？其基本理念是：把常用的功能、控件、基础类、第三方库、权限等公共部分抽离封装，我们称之为基础组件（`baselibs`）；把业务分成 N 个模块进行独立的管理，每一个模块我们称之为业务组件；而所有的业务组件都需要依赖于封装的基础组件，业务组件之间不做依赖，这样的目的是为了让每一个业务模块都能单独运行。而在 APP 层对整个项目的模块进行封装。

> 业务模块之间的跳转可以通过路由（`Arouter`）实现；业务模块之间的通信可以通过消息（`EventBus`）来实现。

## 三、基础搭建 

#### 1、组件框架图

![](/art/00.png)

#### 2、根据组件框架图搭建的项目结构图

![项目结构图](/art/01.png)

#### 3、接下来介绍每个模块

项目中总共有五个 `module` ，包括 3 个业务模块、一个基础模块和一个 `APP` 壳模块。

在建好项目之后我们需要给 3 个 `module` 配置 “集成开发模式” 和 “组件开发模式” 的切换开关，可以在 `gradle.properties` 文件中定义变量 `isModel` ，`isModel=false` 代表是 “集成开发模式” , `isModel=true` 代表是 “组件开发模式” （**注：每次修改isModel的值后一定要Sysn才会生效**）。

![](/art/02.png)

**1）APP 壳模块**

主要就是集成每一个模块，最终打包成一个完整的 `apk` ，其中 `gradle` 做了如下配置，根据配置文件中的 `isModel` 字段来依赖不同的业务组件；

![](/art/03.png)

**2）baselibs 模块**

主要负责封装公共部分，如 `MVP` 架构、 `BaseView` 的封装、网络请求库、图片加载库、工具类以及自定义控件等；
> 为了防止重复依赖，所有的第三方库都放在这个模块，业务模块不做任何第三方依赖，只依赖于 `baselibs` 模块。

`baselibs` 模块的结构如下：

![](/art/04.png)

在 `baselibs` 模块的 `gradle` 中引入的库

```
dependencies {
    implementation fileTree(include: ['*.jar'], dir: 'libs')
    configurations {
        all*.exclude group: 'com.android.support', module: 'support-v13'
    }
    testImplementation rootProject.ext.testDeps["junit"]
    androidTestImplementation rootProject.ext.testDeps["runner"]
    androidTestImplementation rootProject.ext.testDeps["espresso-core"]
    //leakCanary
    debugApi rootProject.ext.testDeps["leakcanary-debug"]
    releaseApi rootProject.ext.testDeps["leakcanary-release"]
    // Support库
    api rootProject.ext.supportLibs
    // 网络请求库
    api rootProject.ext.networkLibs
    // RxJava2
    api rootProject.ext.rxJavaLibs
    // commonLibs
    api rootProject.ext.commonLibs
    kapt rootProject.ext.otherDeps["arouter-compiler"]
}
```

**3）业务模块（module_news、module_video、module_me）**

每一个业务模块在 “集成开发模式” 下以 `library` 的形式存在；在 “组件开发模式” 下以 `application` 的形式存在，可以单独运行。

由于每个业务模块的配置文件都差不多，下面就以 `module_news` 模块为例；

以下是 `module_news` 模块的 `gradle` 配置文件：

```
if (isModule.toBoolean()) {
    apply plugin: 'com.android.application'
} else {
    apply plugin: 'com.android.library'
}
android {
    if (isModule.toBoolean()) {
        applicationId "com.cxz.module.me"
    }
    compileSdkVersion rootProject.ext.android.compileSdkVersion
    defaultConfig {
        minSdkVersion rootProject.ext.android.minSdkVersion
        targetSdkVersion rootProject.ext.android.targetSdkVersion
        versionCode 1
        versionName "1.0"
    }
}
dependencies {
    implementation fileTree(include: ['*.jar'], dir: 'libs')
    testImplementation rootProject.ext.testDeps["junit"]
    androidTestImplementation rootProject.ext.testDeps["runner"]
    androidTestImplementation rootProject.ext.testDeps["espresso-core"]
    implementation project(':baselibs')
    kapt rootProject.ext.otherDeps["arouter-compiler"]
}
```

**4）配置文件 `config.gradle` ，对项目中的第三库、 `app` 的版本等配置**

```
ext {
    android = [
            compileSdkVersion: 28,
            buildToolsVersion: "28.0.3",
            minSdkVersion    : 16,
            targetSdkVersion : 27,
            versionCode      : 1,
            versionName      : "1.0.0"
    ]
    dependVersion = [
            androidSupportSdkVersion: "28.0.0",
            espressoSdkVersion      : "3.0.2",
            retrofitSdkVersion      : "2.4.0",
            glideSdkVersion         : "4.8.0",
            rxJava                  : "2.2.2",
            rxAndroid               : "2.1.0",
            rxKotlin                : "2.3.0",
            anko                    : "0.10.7"
    ]
    supportDeps = [
            "supportv4"        : "com.android.support:support-v4:${dependVersion.androidSupportSdkVersion}",
            "appcompatv7"      : "com.android.support:appcompat-v7:${dependVersion.androidSupportSdkVersion}",
            "cardview"         : "com.android.support:cardview-v7:${dependVersion.androidSupportSdkVersion}",
            "design"           : "com.android.support:design:${dependVersion.androidSupportSdkVersion}",
            "constraint-layout": "com.android.support.constraint:constraint-layout:1.1.3",
            "annotations"      : "com.android.support:support-annotations:${dependVersion.androidSupportSdkVersion}"
    ]
    retrofit = [
            "retrofit"                : "com.squareup.retrofit2:retrofit:${dependVersion.retrofitSdkVersion}",
            "retrofitConverterGson"   : "com.squareup.retrofit2:converter-gson:${dependVersion.retrofitSdkVersion}",
            "retrofitAdapterRxjava2"  : "com.squareup.retrofit2:adapter-rxjava2:${dependVersion.retrofitSdkVersion}",
            "okhttp3LoggerInterceptor": 'com.squareup.okhttp3:logging-interceptor:3.11.0',
            "retrofitConverterMoshi"  : 'com.squareup.retrofit2:converter-moshi:2.4.0',
            "retrofitKotlinMoshi"     : "com.squareup.moshi:moshi-kotlin:1.7.0"
    ]
    rxJava = [
            "rxJava"   : "io.reactivex.rxjava2:rxjava:${dependVersion.rxJava}",
            "rxAndroid": "io.reactivex.rxjava2:rxandroid:${dependVersion.rxAndroid}",
            "rxKotlin" : "io.reactivex.rxjava2:rxkotlin:${dependVersion.rxKotlin}",
            "anko"     : "org.jetbrains.anko:anko:${dependVersion.anko}"
    ]
    testDeps = [
            "junit"                    : 'junit:junit:4.12',
            "runner"                   : 'com.android.support.test:runner:1.0.2',
            "espresso-core"            : "com.android.support.test.espresso:espresso-core:${dependVersion.espressoSdkVersion}",
            "espresso-contrib"         : "com.android.support.test.espresso:espresso-contrib:${dependVersion.espressoSdkVersion}",
            "espresso-intents"         : "com.android.support.test.espresso:espresso-intents:${dependVersion.espressoSdkVersion}",
            "leakcanary-debug"         : 'com.squareup.leakcanary:leakcanary-android:1.6.1',
            "leakcanary-release"       : 'com.squareup.leakcanary:leakcanary-android-no-op:1.6.1',
            "leakcanary-debug-fragment": 'com.squareup.leakcanary:leakcanary-support-fragment:1.6.1',
            "debug-db"                 : 'com.amitshekhar.android:debug-db:1.0.4'
    ]
    commonDeps = [
            "multidex": 'com.android.support:multidex:1.0.3',
            "logger"  : 'com.orhanobut:logger:2.2.0',
            "glide"   : 'com.github.bumptech.glide:glide:4.8.0',
            "eventbus": 'org.greenrobot:eventbus:3.1.1',
            "spinkit" : 'com.github.ybq:Android-SpinKit:1.2.0',
            "arouter" : 'com.alibaba:arouter-api:1.4.0'
    ]
    otherDeps = [
            "arouter-compiler": 'com.alibaba:arouter-compiler:1.2.1'
    ]
    supportLibs = supportDeps.values()
    networkLibs = retrofit.values()
    rxJavaLibs = rxJava.values()
    commonLibs = commonDeps.values()
}

```

**最后别忘记在工程的中 `build.gradle` 引入该配置文件**

```
apply from: "config.gradle"
```

## 四、业务模块之间交互

> 业务模块之间的跳转可以通过路由（`Arouter`）实现；业务模块之间的通信可以通过消息（`EventBus`）来实现。

#### 1、Arouter 实现业务模块之间的跳转

我们在之前已经依赖了 `Arouter` （详细用法参照：[https://github.com/alibaba/ARouter](https://github.com/alibaba/ARouter)），用它来实现跳转只需要以下两步：

**第一步**

- `gradle` 配置

```
kapt {
    arguments {
        arg("AROUTER_MODULE_NAME", project.getName())
    }
    generateStubs = true
}
dependencies {
...
    kapt rootProject.ext.otherDeps["arouter-compiler"]
}
```

**第二步**

- 需要指明目标页面以及要带的参数，然后在调用 `navigation()` 方法；

![](/art/05.png)

**第三步**

- 首先在 `onCreate` 方法调用 `ARouter.getInstance().inject(this)` 注入；
- 然后要用 `@Route` 注解标注页面，并在 `path` 变量中给页面定义一个路径；
- 最后对于传送过来的变量我们直接定义一个同名的字段用 `@Autowired` 变量标注，`Arouter` 会对该字段自动赋值

![](/art/06.png)

#### 2、EventBus 实现业务模块之间的通讯

利用第三方如 `EventBus` 对消息进行管理。在 `baselibs` 组件中的 `BaseActivity` 、 `BaseFragment` 类做了对消息的简单封装，子类只需要重写 `useEventBus()` 返回 `true` 即可对事件的注册。

## 五、搭建过程中遇到的问题

#### 1、AndroidManifest 

我们知道 `APP` 在打包的时候最后会把所有的 `AndroidManifest` 进行合并，所以每个业务组件的 `Activity` 只需要在各自的模块中注册即可。

如果业务组件要单独运行，则需要单独的一个 `AndroidManifest` ，在 `gradle` 的 `sourceSets` 加载不同的 `AndroidManifest` 即可。

![](/art/07.png) 

**gradle 配置**

```
android {
...
    sourceSets {
        main {
            if (isModule.toBoolean()) {
                manifest.srcFile 'src/main/module/AndroidManifest.xml'
            } else {
                manifest.srcFile 'src/main/AndroidManifest.xml'
                //集成开发模式下排除debug文件夹中的所有Java文件
                java {
                    exclude 'debug/**'
                }
                kotlin {
                    exclude 'debug/**'
                }
            }
        }
    }
...
}
```

**注意：集成模式下的 AndroidManifest 不需要配置 Application ，组件模式下的 AndroidManifest 需要单独配置 Application 。**

#### 2、资源文件冲突的问题

不同业务组件里的资源文件的名称可能相同，所以就可能出现资源文件冲突的问题，我们可以通过设置资源的前缀来防止资源文件的冲突。

![](/art/08.png)

**gradle 配置，以 module_news 模块为例**

```
android {
...
    resourcePrefix "news_"
...
}
```

这样配置以后，如果我们在命名资源文件没有加前缀的时候，编译器就会提示我们没加前缀。

**至此， `Android` 基本组件化框架已经搭建完成，如有错误之处还请指正。**

## 五、最后

**完整的项目地址：[https://github.com/iceCola7/AndroidModuleSamples](https://github.com/iceCola7/AndroidModuleSamples)**

