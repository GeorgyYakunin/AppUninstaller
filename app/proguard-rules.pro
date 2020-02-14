# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-printmapping mapping.txt
-verbose
-dontoptimize
-dontpreverify
-dontshrink
-dontskipnonpubliclibraryclassmembers
-dontusemixedcaseclassnames
-keepparameternames
-renamesourcefileattribute SourceFile
-keepattributes *Annotation*
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod

-keepclassmembers class * {
    public void *test*(...);
}

-keep class * extends android.app.Activity
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
}

-keep class com.facebook.** { *; }
-keep class com.androidquery.** { *; }
-keep class com.google.** { *; }
-keep class org.acra.** { *; }
-keep class org.apache.** { *; }
-keep class com.mobileapptracker.** { *; }
-keep class com.nostra13.** { *; }
-keep class net.simonvt.** { *; }
-keep class android.support.** { *; }
-keep class com.nnacres.app.model.** { *; }
-keep class com.facebook.** { *; }
-keep class com.astuetz.** { *; }
-keep class twitter4j.** { *; }
-keep class com.actionbarsherlock.** { *; }
-keep class com.dg.libs.** { *; }
-keep class android.support.v4.** { *; }
-keep class com.bluetapestudio.templateproject.** { *; }
-keep class com.yourideatoreality.model.** { *; }
-keep interface com.yourideatoreality.model.** { *; }
-keep class com.bluetapestudio.** { *; }
-keep interface com.bluetapestudio.** { *; }
# Suppress warnings if you are NOT using IAP:
-dontwarn com.nnacres.app.**
-dontwarn com.androidquery.**
-dontwarn com.google.**
-dontwarn org.acra.**
-dontwarn org.apache.**
-dontwarn com.mobileapptracker.**
-dontwarn com.nostra13.**
-dontwarn net.simonvt.**
-dontwarn android.support.**
-dontwarn com.facebook.**
-dontwarn twitter4j.**
-dontwarn com.astuetz.**
-dontwarn com.actionbarsherlock.**
-dontwarn com.dg.libs.**
-dontwarn  com.bluetapestudio.templateproject.**

-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

# The official support library.
-keep class android.support.v4.app.** { *; }
-keep interface android.support.v4.app.** { *; }

#  Library JARs.
#-keep class de.greenrobot.dao.** { *; }
#-keep interface de.greenrobot.dao.** { *; }
# Library projects.
-keep class com.actionbarsherlock.** { *; }
-keep interface com.actionbarsherlock.** { *; }
#Keep native
-keepclasseswithmembernames class * {
    native <methods>;
}


-dontwarn okio.**
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault

-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**

-keep class com.jaredrummler.android.processes.**{ *; }
-keep interface com.jaredrummler.android.processes.**{ *; }
-keep enum com.jaredrummler.android.processes.**{ *; }

-keep class me.itangqi.waveloadingview.**{ *; }
-keep interface me.itangqi.waveloadingview.**{ *; }
-keep enum me.itangqi.waveloadingview.**{ *; }

-keep class antonkozyriatskyi.circularprogressindicator.**{ *; }
-keep interface antonkozyriatskyi.circularprogressindicator.**{ *; }
-keep enum antonkozyriatskyi.circularprogressindicator.**{ *; }

-keep class org.jetbrains.**{ *; }
-keep interface org.jetbrains.**{ *; }
-keep enum org.jetbrains.**{ *; }

-keep class com.goodiebag.protractorview.**{ *; }
-keep interface com.goodiebag.protractorview.**{ *; }
-keep enum com.goodiebag.protractorview.**{ *; }

-keep class com.karumi.dexter.**{ *; }
-keep interface com.karumi.dexter.**{ *; }
-keep enum com.karumi.dexter.**{ *; }

-keep class com.squareup.**{ *; }
-keep interface com.squareup.**{ *; }
-keep enum com.squareup.**{ *; }

-keep class com.crashlytics.android.**{ *; }
-keep interface com.crashlytics.android.**{ *; }
-keep enum com.crashlytics.android.**{ *; }

-keep class io.reactivex.**{ *; }
-keep interface io.reactivex.**{ *; }
-keep enum io.reactivex.**{ *; }

-keep class org.hamcrest.**{ *; }
-keep interface org.hamcrest.**{ *; }
-keep enum org.hamcrest.**{ *; }

-keep class org.greenrobot.eventbus.**{ *; }
-keep interface org.greenrobot.eventbus.**{ *; }
-keep enum org.greenrobot.eventbus.**{ *; }

-keep class org.reactivestreams.**{ *; }
-keep interface org.reactivestreams.**{ *; }
-keep enum org.reactivestreams.**{ *; }

-keep class com.jaredrummler.android.colorpicker.**{ *; }
-keep interface com.jaredrummler.android.colorpicker.**{ *; }
-keep enum com.jaredrummler.android.colorpicker.**{ *; }


-keep class com.theartofdev.edmodo.cropper.**{ *; }
-keep interface com.theartofdev.edmodo.cropper.**{ *; }
-keep enum com.theartofdev.edmodo.cropper.**{ *; }


-keep class com.opencsv.**{ *; }
-keep interface com.opencsv.**{ *; }
-keep enum com.opencsv.**{ *; }


-keep class com.journeyapps.barcodescanner.**{ *; }
-keep interface com.journeyapps.barcodescanner.**{ *; }
-keep enum com.journeyapps.barcodescanner.**{ *; }


-keep class me.dm7.barcodescanner.**{ *; }
-keep interface me.dm7.barcodescanner.**{ *; }
-keep enum me.dm7.barcodescanner.**{ *; }

-keep class net.sourceforge.zbar.** { *; }

-keep class org.kxml2.**{ *; }
-keep interface org.kxml2.**{ *; }
-keep enum org.kxml2.**{ *; }


-keep class org.xmlpull.v1.**{ *; }
-keep interface org.xmlpull.v1.**{ *; }
-keep enum org.xmlpull.v1.**{ *; }


-dontwarn com.opencsv.**
-dontwarn org.apache.commons.beanutils.**
-dontwarn org.apache.commons.collections.**

# proguard configuration for iText

-keep class org.spongycastle.** { *; }
-dontwarn org.spongycastle.**

-keep class com.itextpdf.** { *; }
-dontwarn com.itextpdf.**

-keep class org.bouncycastle.** { *; }
-dontwarn org.bouncycastle.**


-keep class com.bumptech.glide.** { *; }
-dontwarn com.bumptech.glide.**