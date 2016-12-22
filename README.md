# Android-ScaleLayout

A Simple & Convenience MultiScreen-Support-Library for Android

## The essence is percent scaling.

### different from ``android-percent-support-lib``

1. **More reliable**      
    ``android-percent-support-lib`` The percentage of the parent and child views.       
    ``Android-ScaleLayout`` The percentage of the design and devices screens.       

2. **More convenience**       
    ``android-percent-support-lib`` need to calculate percent.      
    ``Android-ScaleLayout`` directly write the design size on ``layout.xml``.       


## How to look?

![screenhot](/screenhot.png)


## Principle

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

## Usage

### 0. dependencies

```
dependencies {
    compile 'cn.gavinliu.android.lib:ScaleLayout:1.0.2'
}
```

### 1. Initialize

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

Only need to replace ``FrameLayout`` ``LinearLayout`` ``RelativeLayout`` to ``ScaleFrameLayout`` ``ScaleLinearLayout`` ``ScaleRelativeLayout``.

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

### Support Attrs

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
