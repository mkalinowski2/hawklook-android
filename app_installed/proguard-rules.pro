# Do not allow to find out what's the original class name
# See: http://proguard.sourceforge.net/manual/examples.html#stacktrace
# For retracing obfuscated stack trace see: http://proguard.sourceforge.net/manual/retrace/index.html
-renamesourcefileattribute SourceFile
-printmapping ../dev-tools/proguard_mapping.txt
-keepattributes SourceFile,LineNumberTable,*Annotation*,InnerClasses

# See: http://proguard.sourceforge.net/manual/examples.html#repackaging
-repackageclasses 'hawklook'
-allowaccessmodification

# BottomNavigationViewEx
-keep public class android.support.design.widget.BottomNavigationView { *; }
-keep public class android.support.design.internal.BottomNavigationMenuView { *; }
-keep public class android.support.design.internal.BottomNavigationPresenter { *; }
-keep public class android.support.design.internal.BottomNavigationItemView { *; }
-keep public class com.hannesdorfmann.fragmentargs.** { *; }

# Retrofit2
-dontnote retrofit2.Platform
-dontwarn retrofit2.Platform$Java8
-keepattributes Signature
-keepattributes Exceptions
-dontwarn okio.**

#Moshi with Kotlin
-dontwarn javax.annotation.**
-dontwarn org.jetbrains.annotations.**
-keepclasseswithmembers class * {
    @com.squareup.moshi.* <methods>;
}
-keep @com.squareup.moshi.JsonQualifier interface *
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}
-keepclassmembers class com.fewbits.hawklook.domain.** {
  <init>(...);
  <fields>;
}

# RxJava & RxIdler
-dontwarn sun.misc.**
-keep class io.reactivex.plugins.RxJavaPlugins { *; }
-keep class io.reactivex.disposables.CompositeDisposable { *; }

# OkHttp
-dontwarn okhttp3.**

-dontnote **

# LifecycleObserver's empty constructor is considered to be unused by proguard
-keepclassmembers class * implements android.arch.lifecycle.LifecycleObserver {
    <init>(...);
}
# ViewModel's empty constructor is considered to be unused by proguard
-keepclassmembers class * extends android.arch.lifecycle.ViewModel {
    <init>(...);
}
# keep Lifecycle State and Event enums values
-keepclassmembers class android.arch.lifecycle.Lifecycle$State { *; }
-keepclassmembers class android.arch.lifecycle.Lifecycle$Event { *; }
# keep methods annotated with @OnLifecycleEvent even if they seem to be unused
# (Mostly for LiveData.LifecycleBoundObserver.onStateChange(), but who knows)
-keepclassmembers class * {
    @android.arch.lifecycle.OnLifecycleEvent *;
}

-keepclassmembers class * implements android.arch.lifecycle.LifecycleObserver {
    <init>(...);
}

-keep class * implements android.arch.lifecycle.LifecycleObserver {
    <init>(...);
}
-keepclassmembers class android.arch.** { *; }
-keep class android.arch.** { *; }
-dontwarn android.arch.**