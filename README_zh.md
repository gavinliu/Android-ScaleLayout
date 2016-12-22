# Android-ScaleLayout

一个简单的,方便的多屏适配的Android库

## 本质就是百分比缩放

### 和 android-percent-support-lib 的不同

1. **更科学**      
    ``android-percent-support-lib`` 是父控件和子控件的百分比关系      
    ``Android-ScaleLayout`` 是设计界面和设备界面的百分比关系      

2. **更方便**
    ``android-percent-support-lib`` 需要把设计尺寸算成百分比      
    ``Android-ScaleLayout`` 直接把设计尺寸填入``layou.xml``即可      


## How to look?

![screenhot](/screenhot.png)


## 原理

```java
float realPixel = percent * designPixel
```

### Pix Mode

```java
float realPixel = percent * designPixel

float percent = mScreenWidth / designScreenWidth

float designPixel = res.getDimensionPixelSize()
```
```java
float realPixel = mScreenWidth * res.getDimensionPixelSize() / designScreenWidth
```

### DP Mode
```java
float realPixel = percent * designPixel

float percent = mScreenWidth / designScreenWidth
float designPixel = designDP * designDensity // dp to pixel

float designDP = res.getDimensionPixelSize() / mDensity
```
```java
float realPixel = (mScreenWidth * designDensity * getPixelSize()) / (designScreenWidth * mDensity)
```

## 使用

### 0. 依赖

```
dependencies {
    compile 'cn.gavinliu.android.lib:ScaleLayout:1.0.2'
}
```

### 1. 初始化

```java
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        ScaleConfig.create(this,
            1080, // Design Width
            1920, // Design Height
            3,    // Design Density
            ScaleConfig.DIMENS_UNIT_DP);
    }
}
```

### 2. Scale***Layout

只需要把 ``FrameLayout`` ``LinearLayout`` ``RelativeLayout`` 替换成 ``ScaleFrameLayout`` ``ScaleLinearLayout`` ``ScaleRelativeLayout``.

### 3. Scale by width or height

```xml
<attr name="layout_scale_by" format="enum">
    <enum name="width" value="0"/>
    <enum name="height" value="1"/>
</attr>
```
```xml
app:layout_scale_by="width"
```

### 支持的属性

```xml
<attr name="android:layout_width"/>
<attr name="android:layout_height"/>

<attr name="android:layout_margin"/>
<attr name="android:layout_marginLeft"/>
<attr name="android:layout_marginTop"/>
<attr name="android:layout_marginRight"/>
<attr name="android:layout_marginBottom"/>
<attr name="android:layout_marginStart"/>
<attr name="android:layout_marginEnd"/>

<attr name="android:padding"/>
<attr name="android:paddingLeft"/>
<attr name="android:paddingTop"/>
<attr name="android:paddingRight"/>
<attr name="android:paddingBottom"/>
<attr name="android:paddingStart"/>
<attr name="android:paddingEnd"/>
```

## License

MIT
