##ZXingScanLite
A lite version of zxing android camera scan library,configurable and very easy to use and change layout.


##Gradle Configure
1.Add the JitPack repository to your build file
```groovy
allprojects {
        repositories {
            ...
            maven { url "https://jitpack.io" }
        }
}
```
2.Add the dependency
```groovy
dependencies {
        compile 'com.github.maxwell-nc:ZXingScanLite:v1.4'
}
```
##Usage
1.Simple scan without configuration
```java
ZXingScaner.scanBuilder(MainActivity.this).scan();
```
2.Add the Callback
```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    ZXingScaner.resultBuilder(requestCode, resultCode, data)
            .resultListener(new ZXingScaner.onGetResultContentListener() {
                @Override
                public void onGetResultContent(String result) {
                    //result is qrcode text
                }
            })
            .result();

}
```

##Use your layout
1.add in your layout
```xml
<pres.mc.maxwell.library.ui.ScanLayout
    android:id="@+id/sv_scan"
    android:layout_width="match_parent"
    android:layout_height="0dp"
    android:layout_weight="1">

    <TextView
        android:id="@+id/tv_bar"
        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:background="@android:color/black"
        android:textColor="@android:color/white"
        android:text="this is a sample of bar view,click me back."
        android:gravity="center_vertical"
        android:paddingLeft="10dp"/>
		
    <!-- below view is not necessary -->
    <pres.mc.maxwell.library.ui.OverlayView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:bounds_height="200dp"
        app:bounds_left="80dp"
        app:bounds_margin_top="0dp"
        app:bounds_top="120dp"
        app:bounds_width="200dp"
        app:corner_color="@android:color/white"
        app:corner_length="30dp"
        app:corner_stroke_width="4dp"
        app:hint_color="@android:color/white"
        app:hint_margin_top="40dp"
        app:hint_size="24sp"
        app:hint_text="scanning..."
        app:scan_line_color="@android:color/white"
        app:scan_line_height="4dp"
        app:scan_line_speed="10"/>

</pres.mc.maxwell.library.ui.ScanLayout>
```

if you do not set bounds,OverlayView will set in view group center and size was half of view width;

2.config
```java
ZXingScaner.configBuilder()
        .autoFocusInterval(1500L)
        .setCaptureClass(CaptureActivity.class)//if not set,use default
        .scanArea(new Rect(0, 230, 720, 950))//scan area not overlay
        .buildScanAfterConfig(MainActivity.this)
        .scan();
```

3.custom activity
you can use AbsCustomCaptureActivity as parent
```java
public class CaptureActivity extends AbsCustomCaptureActivity {

    @Override
    public void afterCreate(Bundle savedInstanceState) {
	
		//do your job
        findViewById(R.id.tv_bar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public int setContentId() {
        return R.layout.activity_scan;//your custom layout
    }

    @Override
    public int setScanLayoutId() {
        return R.id.sv_scan;//scan layout id in your custom layout
    }

}
```

###Merge Problem
add follow in AndroidManifest.xml
```xml
xmlns:tools="http://schemas.android.com/tools"
<uses-sdk
    tools:overrideLibrary="pres.mc.maxwell.library"/>
```

###Notice
use more configure function with see source comment and method.
this project is change from a network version,but i forget where does it come from,contract me if you know!
