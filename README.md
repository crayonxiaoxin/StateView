# StateView  [ ![Download](https://api.bintray.com/packages/lau/StateView/StateView/images/download.svg?version=1.0.2) ](https://bintray.com/lau/StateView/StateView/1.0.2/link)

![image](https://github.com/crayonxiaoxin/StateView/blob/master/images/demo.gif)

## Gradle引入
```java
implementation 'com.lau:StateView:1.0.2'
```

## 使用方法
### Xml【注意：只能有一个子view或viewgroup】
```xml
<com.lau.StateView
    android:id="+id/stateView"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <LinearLayout
        android:id="@+id/your_content"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:gravity="center"
        android:orientation="vertical">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/you_text" />
    </LinearLayout>
</com.lau.StateView>
```

### java
```java
@Override
public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    stateView = view.findViewById(R.id.stateView);
    //重写retry layout，提供retry点击接口
    stateView.setRetryLayout(R.layout.base_retry, new StateView.setOnRetry() {
        @Override
        public void onRetry(View retryView) {
            retryView.findViewById(R.id.base_retry).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stateView.showLoading();
                    initData();// load data
                }
            });
        }
    });
    stateView.showLoading();
    initData();// load data
}
```

### 四种状态
```java
stateView.showLoading();//加载
stateView.showRetry();//重试
stateView.showContent();//内容
stateView.showEmpty();//空
```
### 通过以下方式自定义layout
```java
public void setLoadingLayout(int resID)
public void setRetryLayout(int resID,setOnRetry listener)
public void setEmptyLayout(int resID)
```
