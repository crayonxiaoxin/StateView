# StateView

## Gradle引入
```java
compile 'com.lau:StateView:1.0.0'
```

## Maven引入
```java
<dependency>
	<groupId>com.lau</groupId>
	<artifactId>StateView</artifactId>
	<version>1.0.0</version>
	<type>pom</type>
</dependency>
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
            android:text="@string/connection_error" />
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
    stateView.setRetryResID(R.layout.base_retry, new StateView.setOnRetry() {
        @Override
        public void onRetry(View retryView) {
            retryView.findViewById(R.id.base_retry).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stateView.showView(StateView.State.LOADING);
                    initData();// load data
                }
            });
        }
    });
    stateView.showView(StateView.State.LOADING);
    initData();// load data
}
```

### 四种状态
```java
stateView.showView(StateView.State.LOADING);//加载
stateView.showView(StateView.State.RETRY);//重试
stateView.showView(StateView.State.CONTENT);//内容
stateView.showView(StateView.State.EMPTY);//空
```
### 通过以下方式自定义layout
```java
public void setLoadingResID(int resID)
public void setRetryResID(int resID,setOnRetry listener)
public void setEmptyResID(int resID)
```
