# Android-ScaleLayout

一个简单的,方便的多屏适配的Android库

# 本质就是百分比缩放

## 和 android-percent-support-lib 的不同

1. **更科学**  
    ``android-percent-support-lib`` 是父控件和子控件的百分比关系  
    ``Android-ScaleLayout`` 是设计界面和设备界面的百分比关系

2. **更可靠**  
    ``android-percent-support-lib`` 可能会导致大量尺寸的精度丢失  
    ``Android-ScaleLayout`` 精度最多丢失1px  

3. **更方便**  
    ``android-percent-support-lib`` 需要把设计尺寸算成百分比  
    ``Android-ScaleLayout`` 直接把设计尺寸填入``layou.xml``即可

## Android有良好的多屏适配系统,为何设计ScaleLayout?



# 如何使用

## 基本原理
+ 设计属性  
    设计手机的宽高,像素密度,设计尺寸(dp)
+ 设备属性
    设备手机的宽高,像素密度

```java
float realPixel = percent * designPixel

float percent = mScreenWidth / designScreenWidth // 屏幕比
float designDP = getPixelSize() / mDensity // DesignDP是写入在xml文件中的，需要通过 getDimensionPixelSize() 还原出来
float designPixel = designDP * designDensity // dp 转 pixel
```
```java
float realPixel = (mScreenWidth * designDensity * getPixelSize()) / (designScreenWidth * mDensity)
```

## 属性

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <declare-styleable name="ScaleLayout">

        <attr name="layout_scale_by" format="enum">
            <enum name="width" value="0" />
            <enum name="height" value="1" />
        </attr>

        <attr name="layout_design_width" format="dimension" />
        <attr name="layout_design_height" format="dimension" />
        <attr name="layout_design_density" format="integer" />

        <attr name="layout_width" format="dimension" />
        <attr name="layout_height" format="dimension" />

        <attr name="layout_margin" format="dimension" />
        <attr name="layout_marginLeft" format="dimension" />
        <attr name="layout_marginTop" format="dimension" />
        <attr name="layout_marginRight" format="dimension" />
        <attr name="layout_marginBottom" format="dimension" />
        <attr name="layout_marginStart" format="dimension" />
        <attr name="layout_marginEnd" format="dimension" />

        <attr name="layout_paddingLeft" format="dimension" />
        <attr name="layout_paddingTop" format="dimension" />
        <attr name="layout_paddingRight" format="dimension" />
        <attr name="layout_paddingBottom" format="dimension" />
    </declare-styleable>

</resources>
```

## 使用

```xml
<cn.gavinliu.android.lib.scale.ScaleRelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:layout_design_density="@integer/app_design_density"
    app:layout_design_height="@dimen/app_design_height"
    app:layout_design_width="@dimen/app_design_width">

    <TextView
        android:id="@+id/view"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:background="#46b34b"
        app:layout_height="120dp"
        app:layout_marginLeft="12dp"
        app:layout_marginTop="8dp"
        app:layout_scale_by="width"
        app:layout_width="166dp" />

    <cn.gavinliu.android.lib.scale.ScaleRelativeLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_below="@id/view"
        android:background="#46b"
        app:layout_design_density="@integer/app_design_density"
        app:layout_design_height="@dimen/app_design_height"
        app:layout_design_width="@dimen/app_design_width"
        app:layout_height="300dp"
        app:layout_marginTop="5dp"
        app:layout_width="300dp">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:background="#46b34b"
            app:layout_height="120dp"
            app:layout_marginLeft="12dp"
            app:layout_marginTop="8dp"
            app:layout_scale_by="width"
            app:layout_width="166dp" />

    </cn.gavinliu.android.lib.scale.ScaleRelativeLayout>

</cn.gavinliu.android.lib.scale.ScaleRelativeLayout>
```

## 注意事项

1. 缩放方式
    上下滑动的界面按**屏幕宽**等比缩放  
    左右滑动的界面按**屏幕高**等比缩放  
2.
    ``cn.gavinliu.android.lib.scale.ScaleLayout`` 必须包含 layout_design_*  
    ``layout_scale_by`` 默认值为 width

# License

MIT
