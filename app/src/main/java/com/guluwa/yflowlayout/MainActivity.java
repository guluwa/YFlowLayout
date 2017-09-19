package com.guluwa.yflowlayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String[] strs = new String[]{
            "Welcome", "hello", "world", "theme", "activity", "android", "studio",
            "flowlayout", "savedInstanceState", "Bundle", "findViewById", "LayoutInflater",
            "label_layout", "TextView", "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz"
    };
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final YFlowLayout mFlowLayout = findViewById(R.id.mFlowLayout);

        final LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
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
//        index = 1;
//        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                TextView textView = (TextView) inflater.inflate(R.layout.label_layout, mFlowLayout, false);
//                textView.setText("hello world" + index++);
//                mFlowLayout.addView(textView);
//            }
//        });
    }
}
