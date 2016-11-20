# Android-ScaleLayout

一个简单的,方便的多屏适配的Android库

## 本质就是百分比缩放

### 和 android-percent-support-lib 的不同

1. **更科学**  
    ``android-percent-support-lib`` 是父控件和子控件的百分比关系  
    ``Android-ScaleLayout`` 是设计界面和设备界面的百分比关系

2. **更可靠**  
    ``android-percent-support-lib`` 可能会导致大量尺寸的精度丢失  
    ``Android-ScaleLayout`` 精度最多丢失1px  

3. **更方便**  
    ``android-percent-support-lib`` 需要把设计尺寸算成百分比  
    ``Android-ScaleLayout`` 直接把设计尺寸填入``layou.xml``即可


## How to look?

![screenhot](/screenhot.png)


## 基本原理

+ 设计属性  
    设计手机的宽高,像素密度,设计尺寸
+ 设备属性
    设备手机的宽高,像素密度

```java
float realPixel = percent * designPixel
```

### Pix 模式

```java
float realPixel = percent * designPixel

float percent = mScreenWidth / designScreenWidth

float designPixel = res.getDimensionPixelSize()
```
```java
float realPixel = mScreenWidth * res.getDimensionPixelSize() / designScreenWidth
```

### DP 模式
```java
float realPixel = percent * designPixel

float percent = mScreenWidth / designScreenWidth // 屏幕比
float designPixel = designDP * designDensity // dp 转 pixel

float designDP = res.getDimensionPixelSize() / mDensity // DesignDP是写入在xml文件中的，需要通过 pix/density 还原出来
```
```java
float realPixel = (mScreenWidth * designDensity * getPixelSize()) / (designScreenWidth * mDensity)
```

## 如何使用

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

### 初始化

```java
ScaleConfig.create(this,
    1080, // 设计宽
    1920, // 设计高
    3,    // 设计像素密度
    ScaleConfig.DIMENS_UNIT_DP); // 填入的是 dp or pix
```

### 接入

只需要把 FrameLayout，LinearLayout，RelativeLayout 换成 ScaleFrameLayout，ScaleLinearLayout，ScaleRelativeLayout 即可。

### 注意事项

1. 建议缩放方式
    上下滑动的界面按**屏幕宽**等比缩放  
    左右滑动的界面按**屏幕高**等比缩放

## License

MIT
