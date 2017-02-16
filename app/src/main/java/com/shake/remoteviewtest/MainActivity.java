package com.shake.remoteviewtest;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button progress = (Button) findViewById(R.id.progress);
        Button doubleaction = (Button) findViewById(R.id.doubleaction);
        Button customer = (Button) findViewById(R.id.customer);
        Button list = (Button) findViewById(R.id.list);
        Button bigpic = (Button) findViewById(R.id.bigpic);
        Button moretext = (Button) findViewById(R.id.moretext);
        Button normal = (Button) findViewById(R.id.normal);


        Intent intent = new Intent(this, OpenActivity.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this, (int) SystemClock.uptimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        /**
         * 发送单行文本通知
         */
        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationUtils utils = new NotificationUtils(MainActivity.this, 1000);
                utils.sendSingleLineNotification("ticker", "单行文本标题", "单行文本内容", R.mipmap.ic_launcher, pendingIntent, true, true, true);
            }
        });

        /**
         * 发送多行文本通知
         */
        moretext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationUtils utils = new NotificationUtils(MainActivity.this, 1001);
                utils.sendMoreLineNotification("ticker", "多行文本标题", "这是内容,这是内容,这是内容,这是内容,这是内容,这是内容,这是内容",
                        R.mipmap.ic_launcher, pendingIntent, true, true, true);
            }
        });


        /**
         * 大图通知
         */
        bigpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationUtils utils = new NotificationUtils(MainActivity.this,1002);
                utils.sendBigPicNotification("ticker", "大图通知标题", "大图通知内容", R.mipmap.ic_launcher, pendingIntent, true, true, true);
            }
        });

        /**
         * 列表通知
         */
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> contents = new ArrayList<String>();
                contents.add("一部好的电影，可以改变你对世界的看法！");
                contents.add("程序员必知的七个图形工具");
                contents.add("Splash启动界面秒开的正确打开模式");
                contents.add("全栈工程师如何快速构建一个Web应用");
                NotificationUtils utils = new NotificationUtils(MainActivity.this,1003);
                utils.sendListNotification("ticker", "列表通知标题", "列表通知内容", R.mipmap.ic_launcher, pendingIntent,contents, true, true, true);
            }
        });

        /**
         * 双折叠按钮通知
         */
        doubleaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PendingIntent left = PendingIntent.getActivity(MainActivity.this,0,new Intent(MainActivity.this,OpenActivity.class),PendingIntent.FLAG_UPDATE_CURRENT);
                PendingIntent right = PendingIntent.getActivity(MainActivity.this,0,new Intent(MainActivity.this,OpenActivity.class),PendingIntent.FLAG_UPDATE_CURRENT);
                //发送通知
                NotificationUtils utils = new NotificationUtils(MainActivity.this,1003);
                utils.sendActionNotification("ticker", "双折叠按钮通知标题", "双折叠按钮通知内容", R.mipmap.ic_launcher, pendingIntent,
                        R.mipmap.ic_launcher,"facebook",left, R.mipmap.ic_launcher,"wechat",right, true, true, true);

            }
        });

        /**
         * 进度条通知
         */
        progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationUtils utils = new NotificationUtils(MainActivity.this, 1000);
                utils.sendProgressNotification("ticker", "进度条通知文本标题", "进度条通知文本内容", R.mipmap.ic_launcher, pendingIntent, true, true, true);
            }
        });


        /**
         * 自定义通知
         */
        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PendingIntent remotePending = PendingIntent.getActivity(MainActivity.this,0,new Intent(MainActivity.this,OpenActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

                RemoteViews contentView = new RemoteViews(getPackageName(),R.layout.remoteview);
                contentView.setTextViewText(R.id.share_content, "这是自定义View");
                contentView.setOnClickPendingIntent(R.id.share_facebook, remotePending);
                contentView.setOnClickPendingIntent(R.id.share_content, remotePending);
                //用来调用子View中需要一个Int型参数的方法
                contentView.setInt(R.id.share_content, "setTextColor", Color.WHITE);

                RemoteViews bigContentView = new RemoteViews(getPackageName(),R.layout.bigcontentview);
                bigContentView.setTextViewText(R.id.share_content,"这是自定义View");
                contentView.setTextViewText(R.id.share_content, "这是自定义View");
                contentView.setOnClickPendingIntent(R.id.share_facebook, remotePending);
                contentView.setOnClickPendingIntent(R.id.share_content, remotePending);
                contentView.setInt(R.id.share_content, "setTextColor", Color.WHITE);

                NotificationUtils utils = new NotificationUtils(MainActivity.this,1003);
                utils.sendCustomerNotification("ticker", "title", "content", R.mipmap.ic_launcher, pendingIntent, contentView, bigContentView, true, true, true);


            }
        });

    }
}
