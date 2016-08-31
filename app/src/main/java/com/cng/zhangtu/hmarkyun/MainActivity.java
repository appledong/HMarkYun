package com.cng.zhangtu.hmarkyun;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cng.zhangtu.hmarkyun.view.TagCloudView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TagCloudView tagcloud;

    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tagcloud = (TagCloudView) findViewById(R.id.tagcloud);
        List<String> list = new ArrayList<>();
        list.add("奥林匹克");
        list.add("奥运");
        list.add("奥运精神");
        list.add("中国共产党");
        list.add("没有共产党");
        list.add("没有新中国");
        list.add("很好啊大链");
        list.add("济南");
        list.add("汉峪金谷");
        list.add("奥体中心");
        list.add("绿色旅游");
        list.add("天安门");
        list.add("颐和园");
        list.add("大明湖");
        list.add("夏雨荷");
        list.add("泰山");
        list.add("轰炸北京遗址");
        list.add("元明清故宫");
        list.add("新家遗址");
        tagcloud.bindData(list);
        TextView textview_ref = (TextView) findViewById(R.id.textview_ref);
        textview_ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("dongdianzhou", "添加一个标签");
                tagcloud.addItem("添加标签" + index);
                index++;
            }
        });
    }
}
