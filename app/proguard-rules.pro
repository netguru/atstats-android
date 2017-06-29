# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/maciek/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# base option from *App Dev Note*
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-keepattributes LineNumberTable,SourceFile,Signature,*Annotation*,Exceptions,InnerClasses

# Keep native methods
-keepclassmembers class * {
    native <methods>;
}

# remove log call
-assumenosideeffects class android.util.Log {
    public static *** d(...);
}
-assumenosideeffects class timber.log.Timber {
    public static *** d(...);
}

# app compat-v7
-keep class android.support.v7.widget.SearchView { *; }

# FragmentArgs
-keep class com.hannesdorfmann.fragmentargs.** { *; }

# Gson
-keep class sun.misc.Unsafe { *; }

# retrofit
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
-dontnote retrofit2.Platform
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
-dontwarn retrofit2.Platform$Java8
-keepattributes Signature
-keepattributes Exceptions

# dagger
-keepclassmembers,allowobfuscation class * {
    @javax.inject.* *;
    @dagger.* *;
    <init>();
}
-keep class javax.inject.** { *; }
-keep class **$$ModuleAdapter
-keep class **$$InjectAdapter
-keep class **$$StaticInjection
-keep class dagger.** { *; }
-dontwarn dagger.internal.codegen.**

# xlog
-keep class com.promegu.xlog.** { *; }
-dontwarn javax.lang.**
-dontwarn javax.tools.**

# stetho
-dontwarn org.apache.http.**
-keep class com.facebook.stetho.dumpapp.** { *; }
-keep class com.facebook.stetho.server.** { *; }
-dontwarn com.facebook.stetho.dumpapp.**
-dontwarn com.facebook.stetho.server.**

# leak canary
-keep class org.eclipse.mat.** { *; }
-keep class com.squareup.leakcanary.** { *; }
-dontwarn android.app.Notification

# fabric
-dontwarn com.crashlytics.android.**

# rx
-keep class rx.internal.util.unsafe.** { *; }
-dontwarn sun.misc.Unsafe
-dontwarn java.lang.invoke.*

# glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**

# BugTags
-keep class com.bugtags.library.** {*;}
-dontwarn org.apache.http.**
-dontwarn android.net.http.AndroidHttpClient
-dontwarn com.bugtags.library.**

# AutoBundle
-keepclasseswithmembernames class * {
    @com.yatatsu.autobundle.AutoBundleField <fields>;
}

# AutoGson
-keepclassmembers class **$AutoValue_*$GsonTypeAdapter {
    void <init>(com.google.gson.Gson);
}

# AutoParcel
-keep class **AutoValue_*$1 { }
-keepclassmembers class * implements android.os.Parcelable {
    static ** CREATOR;
}
