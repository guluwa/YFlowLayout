# YFlowLayout
流式布局、支持点击事件
 
![demo](https://github.com/guluwa/YFlowLayout/blob/master/device-2017-09-19-132154.png)
 
* 引入
 
```java
在build.gradle中添加

 dependencies {
    compile 'cn.guluwa:yflowlayout:0.0.1'
}
```
 
* 用法
 
```java
在layout文件中添加 

<com.guluwa.yflowlayout.YFlowLayout
        android:id="@+id/mFlowLayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="#847f7f"
        android:padding="20dp" />
        
 在activity中添加
 
 YFlowLayout mFlowLayout = findViewById(R.id.mFlowLayout);

        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        for (int i = 0; i < strs.length; i++) {
            TextView view = (TextView) inflater.inflate(R.layout.label_layout, mFlowLayout, false);
            view.setText(strs[i]);
            if (i > 10)
                view.setTextColor(getResources().getColor(R.color.colorAccent));
            mFlowLayout.addView(view);
        }
        mFlowLayout.setListener(new OnItemClickListener() {
            @Override
            public void onClick(int position, View view) {
                Toast.makeText(MainActivity.this, strs[position], Toast.LENGTH_SHORT).show();
            }
        });
```
