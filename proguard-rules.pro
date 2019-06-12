
#-libraryjars class_path //应用的依赖包，如Android-support-v4
#-keep [,modifier,...] class_specification //这里的keep就是保持的意思，意味着不混淆某些类
#-keepclassmembers [,modifier,...] class_specification //同样的保持，不混淆类的成员
#-keepclasseswithmembers [,modifier,...] class_specification //不混淆类及其成员
#-keepnames class_specification //不混淆类及其成员名
#-keepclassmembernames class_specification //不混淆类的成员名
#-keepclasseswithmembernames class_specification //不混淆类及其成员名
#-assumenosideeffects class_specification //假设调用不产生任何影响，在proguard代码优化时会将该调用remove掉。如system.out.println和Log.v等等
#-dontwarn [class_filter] //不提示warnning

#表示混淆时不使用大小写混合类名
-dontusemixedcaseclassnames
#表示不跳过library中的非public的类
-dontskipnonpubliclibraryclasses

 -keep class android.support.v4.**
 -keep class android.support.** { *; }
 -keep class android.support.v4.** { *; }
 -keep public class * extends android.support.v4.**
 -keep class android.support.v7.** { *; }
 -keep public class * extends android.support.v7.**
 -keep interface android.support.v7.app.** { *; }
 -dontwarn android.support.**

 -dontwarn android.support.v4.**
 -keep class android.support.v4.app.** { *; }
 -keep interface android.support.v4.app.** { *; }
 -keep public class * extends android.app.Application

 -dontwarn android.support.v7.**
 -keep class android.support.v7.internal.** { *; }
 -keep interface android.support.v7.internal.** { *; }
 -keep class android.support.v7.** { *; }
 #四大组件
 -keep public class * extends android.app.Activity
 -keep public class * extends android.app.Fragment
 -keep public class * extends android.app.Application
 -keep public class * extends android.app.Service
 -keep public class * extends android.content.BroadcastReceiver
 -keep public class * extends android.content.ContentProvider
 -keep public class * extends android.app.backup.BackupAgentHelper
 -keep public class * extends android.preference.Preference

# Optimization is turned off by default. Dex does not like code run
# through the ProGuard optimize and preverify steps (and performs some
# of these optimizations on its own).
-dontoptimize
##表示不进行校验,这个校验作用 在java平台上的
-dontpreverify
# Note that if you want to enable optimization, you cannot just
# include optimization flags in your own project configuration file;
# instead you will need to point to the
# "proguard-android-optimize.txt" file instead of this one from your
# project.properties file.

-keepattributes *Annotation*
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService

# For native methods, see http://proguard.sourceforge.net/manual/examples.html#native
-keepclasseswithmembernames class * {
    native <methods>;
}

# keep setters in Views so that animations can still work.
# see http://proguard.sourceforge.net/manual/examples.html#beans
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclassmembers class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator CREATOR;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

# The support library contains references to newer platform versions.
# Don't warn about those in case this app is linking against an older
# platform version.  We know about them, and they are safe.
-dontwarn android.support.**
# Understand the @Keep support annotation.
-keep class android.support.annotation.Keep
-keep @android.support.annotation.Keep class * {*;}
-keepclasseswithmembers class * {
    @android.support.annotation.Keep <methods>;
}
-keepclasseswithmembers class * {
    @android.support.annotation.Keep <fields>;
}
-keepclasseswithmembers class * {
    @android.support.annotation.Keep <init>(...);
}
# OkHttp3
-dontwarn javax.annotation.**
-dontwarn javax.inject.**
-dontwarn okhttp3.logging.**
-keep class okhttp3.internal.**{*;}
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-dontwarn okio.**
# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
# RxJava RxAndroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

# fasterxml
-keep class com.fasterxml.jackson.annotation.** { *; }

-dontwarn com.fasterxml.jackson.databind.**

-dontwarn org.conscrypt.**
# EventBus
#EventBus3.0配置
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
# 自身混淆
-keep public class xyz.chengqian.basesdk.**{*;}
