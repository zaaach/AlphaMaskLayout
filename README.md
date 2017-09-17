# AlphaMaskLayout

[![Platform](https://img.shields.io/badge/platform-android-green.svg)](http://developer.android.com/index.html)
[![API](https://img.shields.io/badge/API-9%2B-yellow.svg?style=flat)](https://android-arsenal.com/api?level=9)

当`popupwindow`或者`dialog`弹出的时候，指定某个布局有一个透明度的过渡效果

1.支持自定义开始结束透明度、动画时间

2.透明度动画可以中断继续或返回

→_→城市选择推荐使用[CityPicker](https://github.com/zaaach/CityPicker)

# Gif

![image](https://github.com/zaaach/AlphaMaskLayout/raw/master/art/screen.gif)

# Install

Gradle:

```groovy
compile 'com.zaaach:alphamasklayout:1.1'
```

or Maven:

```groovy
<dependency>
  <groupId>com.zaaach</groupId>
  <artifactId>alphamasklayout</artifactId>
  <version>1.1</version>
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



### Step1:

在XML中添加

```xml
<com.zaaach.alphamasklayout.AlphaMaskLayout
	android:id="@+id/mask_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_above="@id/layout_bottom"
    android:foreground="#000"
    aml:aml_alpha_from="0"
    aml:aml_alpha_to="127"
    aml:aml_duration="600">
    //your layout here
    //...
    //...
</com.zaaach.alphamasklayout.AlphaMaskLayout>
```
其中`android:foreground="#000"`就是遮罩层的颜色，不设置则使用默认值`#1f1f1f`.

```java
//you can also do in java like this:
maskLayout.setAlphaFrom(0);
maskLayout.setAlphaTo(127);
maskLayout.setDuration(600);
```

### Step2:

在`activity`中调用

```java
//显示
moreBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        View content = LayoutInflater.from(MainActivity.this).inflate(R.layout.popup, null);
        mPopupWindow = new PopupWindow(content, 300, 400);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        mPopupWindow.showAsDropDown(moreBtn);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
              @Override
              public void onDismiss() {
                maskLayout.hideMask();
              }
        });
        maskLayout.showMask();
      }
});
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