
#============================== 项目  ==============================
# data包下不可混淆  注意:需要改成你自己的
-keep class com.czq.kotlinarch.data.**{*;}

# 如果有自定义分页策略 即 自定义类似 com.czq.kotlin_arch.paging 包下的东西,也要避免混淆,因为我用了反射

# kotlinArch框架
-keep class com.czq.kotlin_arch.**{*;}

#============================== 压缩参数配置 ==============================
# 参考 https://www.jianshu.com/p/7436a1a32891  https://www.jianshu.com/p/be7ec1819d2f
# 表示proguard对代码进行迭代优化的次数，Android一般为5
-optimizationpasses 5

# 不使用大小写混合类名
-dontusemixedcaseclassnames

# 不跳过library中的非public的类
-dontskipnonpubliclibraryclasses

# 不进行预校验，预校验是作用在Java平台上的，Android平台上不需要这项功能，去掉之后还可以加快混淆速度
-dontpreverify

# 优化已默认关闭。 Dex不喜欢通过ProGuard优化和预验证步骤运行代码（并自行执行某些优化）
-dontoptimize

# 不输出提示
-dontnote

# 输出混淆日志
-verbose

#============================== Android ==============================
# 保留注解参数
-keepattributes *Annotation*
-keep class * extends java.lang.annotation.Annotation {*;}
-keep class * implements java.lang.annotation.Annotation {*;}

# 泛型与反射
-keepattributes Signature
-keepattributes EnclosingMethod

# 不混淆内部类
-keepattributes InnerClasses

# 保持Support库不被混淆
-keep class android.support.**{*;}
# 保持androidx库不被混淆
-keep class androidx.**{*;}

# 保持androidx库不被混淆
-keep class com.google.android.material.** {*;}
-keep class androidx.** {*;}
-keep public class * extends androidx.**
-keep interface androidx.** {*;}
-dontwarn com.google.android.material.**
-dontnote com.google.android.material.**
-dontwarn androidx.**

# 保持Parcelable不被混淆
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}
# 保持Serializable不被混淆
-keepnames class * implements java.io.Serializable
-keep public class * implements java.io.Serializable {
   public *;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# 保持枚举类型部分方法不要混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# 保持R文件不被混淆
-keep class **.R{*;}

# 保持 native 方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

# 保留Google原生服务需要的类
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService

# java sdk
-dontwarn javax.lang.model.**
-dontwarn java.lang.invoke.*

#第三方库
#============================== Retrofit ==============================
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-keep class retrofit2.**{*;}

#============================== OKHTTP ==============================
-dontwarn okhttp3.**
# JSR 305 annotations are for embedding nullability information.
-dontwarn javax.annotation.**
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*


#============================== Glide ==============================
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
#============================== FastJson ==============================

-keep class kotlin.reflect.jvm.internal.** { *; }
-dontwarn com.alibaba.fastjson.**
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.alibaba.fastjson.** {*;}

#============================== RxJava ==============================
-dontwarn io.reactivex.**
-keep class io.reactivex.** { *; }
# retrolambda
-dontwarn java.lang.invoke.*

#============================== RxBus ==============================
-keep class com.hwangjr.rxbus.**{*;}


