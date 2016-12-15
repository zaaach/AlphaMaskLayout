# AlphaMaskLayout

[![Platform](https://img.shields.io/badge/platform-android-green.svg)](http://developer.android.com/index.html)
[![API](https://img.shields.io/badge/API-9%2B-yellow.svg?style=flat)](https://android-arsenal.com/api?level=9)

1.支持自定义开始结束透明度、动画时间

2.透明度可以中断，并从中断的地方继续或者反转

城市选择推荐使用[CityPicker](https://github.com/zaaach/CityPicker)

# Gif

![image](https://github.com/zaaach/AlphaMaskLayout/raw/master/art/screen.gif)

# Install

Gradle:

```groovy
compile 'com.zaaach:alphamasklayout:1.0'
```

or Maven:

```xm
<dependency>
  <groupId>com.zaaach</groupId>
  <artifactId>alphamasklayout</artifactId>
  <version>1.0</version>
  <type>pom</type>
</dependency>
```

or 下载library手动导入.

# Usage

`AlphaMaskLayout` extends `FrameLayout`.

`attrs` for AlphaMaskLayout

```xml
<declare-styleable name="AlphaMaskLayout">
        <!--开始透明度0~255-->
        <attr name="aml_alpha_from" format="integer"/>
        <!--结束透明度0~255-->
        <attr name="aml_alpha_to" format="integer"/>
        <!--透明度动画时间长度-->
        <attr name="aml_duration" format="integer"/>
</declare-styleable>
```



### step1:

在XML中添加

```xml
<com.zaaach.alphamasklayout.AlphaMaskLayout
	android:id="@+id/mask_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_above="@id/layout_bottom"
    android:foreground="#FFF"
    aml:aml_alpha_from="0"
    aml:aml_alpha_to="180"
    aml:aml_duration="1200">
    //your layout here
</com.zaaach.alphamasklayout.AlphaMaskLayout>
```
其中`android:foreground="#FFF"`就是遮罩层的颜色，不设置则使用默认值`#233333`.

```java
//you can also do in java like this:
maskLayout.setAlphaFrom(0);
maskLayout.setAlphaTo(160);
maskLayout.setDuration(1200);
maskLayout.setForeground(...);
```

### Step2:

在`activity`中调用

```java
//显示
maskLayout.showMask();
//隐藏
maskLayout.hideMask();
//监听器
maskLayout.setOnAlphaFinishedListener(new AlphaMaskLayout.OnAlphaFinishedListener() {
            @Override
            public void onShowFinished() {
                Toast.makeText(MainActivity.this, "show finished", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onHideFinished() {
                Toast.makeText(MainActivity.this, "hide finished", Toast.LENGTH_SHORT).show();
            }
        });
```

### Step3:

Done! hope it will be useful.

# Ad
我的[动漫周边X宝店]( https://shop238932691.taobao.com/) ，希望您可以关注下(dan)：

![二维码](https://img.alicdn.com/imgextra/i1/769720206/TB2AnBVar0kpuFjy0FjXXcBbVXa_!!769720206.png)