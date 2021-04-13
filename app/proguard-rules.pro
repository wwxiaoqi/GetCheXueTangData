-ignorewarnings
-keep public class * extends android.os.Binder
-keepclassmembers enum * {
    **[] $VALUES;
    public *;
}




-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}

-keep public class okhttp3.** extends java.lang.Exception
-keepattributes *Annotation*
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**
-keep class io.fabric.** { *; }
-renamesourcefileattribute Proguard
-keepattributes SourceFile, LineNumberTable

-obfuscationdictionary dictionary-drakeet.txt
-classobfuscationdictionary dictionary-drakeet.txt
-packageobfuscationdictionary dictionary-drakeet.txt

# MeiZuFingerprint
-keep class com.fingerprints.service.** { *; }
# SmsungFingerprint
-keep class com.samsung.android.sdk.** { *; }

-keep class org.ocpsoft.prettytime.i18n.** { *; }
-keep class org.ocpsoft.prettytime.** { *; }


#-keep class com.blankj.** { *; }



#-keep class com.android.vending.billing.**

# okhttp
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**

-repackageclasses ''
-allowaccessmodification

-dontwarn retrofit2.**

-dontwarn rx.internal.util.unsafe.**

# glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}

-dontwarn com.samsung.android.**

-dontwarn me.drakeet.support.about.provided.**
-dontwarn com.fingerprints.service.FingerprintManager
-dontwarn android.os.ServiceManager

# view pager
-keep public class androidx.viewpager.widget.ViewPager {
    private Scroller mScroller;
}

# Retrofit
# Retain generic type information for use by reflection by converters and adapters.
# Retain service method parameters.
-keepclassmembernames,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# auto dispose
-dontwarn com.google.errorprone.annotations.DoNotMock

-keep public class * extends me.drakeet.timemachine.Savable
-keep public class com.drakeet.purewriter.cloud.CloudFile
-keep public class androidx.preference.EditTextPreference
-keep class * extends com.drakeet.purewriter.cloud.CloudFile

-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int d(...);
    public static int w(...);
    public static int v(...);
    public static int i(...);
}

-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
    static void checkParameterIsNotNull(java.lang.Object, java.lang.String);
    static void checkExpressionValueIsNotNull(java.lang.Object, java.lang.String);
    static void checkNotNullExpressionValue(java.lang.Object, java.lang.String);
    static void checkReturnedValueIsNotNull(java.lang.Object, java.lang.String, java.lang.String);
    static void checkReturnedValueIsNotNull(java.lang.Object, java.lang.String);
    static void checkFieldIsNotNull(java.lang.Object, java.lang.String, java.lang.String);
    static void checkFieldIsNotNull(java.lang.Object, java.lang.String);
    static void checkNotNull(java.lang.Object, java.lang.String);
    static void checkNotNullParameter(java.lang.Object, java.lang.String);
    static void throwUninitializedPropertyAccessException(java.lang.String);
}

-assumenosideeffects class java.io.PrintStream {
     public void println(%);
     public void println(**);
}

# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.** { *; }

-keep public class com.android.vending.billing.IInAppBillingService
