package com.shake.remoteviewtest;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by shake on 17-2-16.
 * 通知的工具类
 */
public class NotificationUtils {

    NotificationManager mManager;
    NotificationCompat.Builder mBuilder;

    int notificationId = -1;

    Context mContext;

    public NotificationUtils(Context context, int notificationId) {
        this.mContext = context;
        this.notificationId = notificationId;

        //获取通知的Manager
        mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //实例化Builder
        mBuilder = new NotificationCompat.Builder(context);
    }

    /**
     * 通用的设置
     *
     * @param ticker        状态栏文字
     * @param title         通知栏标题
     * @param content       通知栏内容
     * @param smallIcon     小图标
     * @param pendingIntent 触发的intent
     * @param sound         声音
     * @param vibrate       震动
     * @param lights        闪光
     */
    public void sample(String ticker, String title, String content, int smallIcon, PendingIntent pendingIntent, boolean sound, boolean vibrate, boolean lights) {

        //状态栏文字
        mBuilder.setTicker(ticker);
        //通知栏标题
        mBuilder.setContentTitle(title);
        //通知栏内容
        mBuilder.setContentText(content);
        //触发的intent
        mBuilder.setContentIntent(pendingIntent);
        //设置颜色，可以给5.0以上版本设置背景色
        mBuilder.setColor(Color.RED);
        //设置小图标
        mBuilder.setSmallIcon(smallIcon);
        //大图标(这边同时设置了小图标跟大图标，在5.0及以上版本通知栏里面的样式会有所不同)
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher));
        //设置该条通知时间
        mBuilder.setWhen(System.currentTimeMillis());
        //设置为true，点击该条通知会自动删除，false时只能通过滑动来删除
        mBuilder.setAutoCancel(true);
        //设置优先级，级别高的排在前面
        mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
        int defaults = 0;
        if (sound) {
            defaults |= Notification.DEFAULT_SOUND;
        }
        if (vibrate) {
            defaults |= Notification.DEFAULT_VIBRATE;
        }
        if (lights) {
            defaults |= Notification.DEFAULT_LIGHTS;
        }
        //设置声音、闪光、震动
        mBuilder.setDefaults(defaults);
        //设置是否为一个正在进行中的通知，这以类型的通知将无法删除
        mBuilder.setOngoing(true);

    }


    /**
     * 单行文本通知
     *
     * @param ticker
     * @param title
     * @param content
     * @param smallIcon
     * @param pendingIntent
     * @param sound
     * @param vibrate
     * @param lights
     */
    public void sendSingleLineNotification(String ticker, String title, String content, int smallIcon, PendingIntent pendingIntent, boolean sound, boolean vibrate, boolean lights) {
        //通用设置
        sample(ticker, title, content, smallIcon, pendingIntent, sound, vibrate, lights);
        //实例化Notification
        Notification notification = mBuilder.build();
        //发送通知
        send(notification);
    }

    /**
     * 多行文本通知
     *
     * @param ticker
     * @param title
     * @param content
     * @param smallIcon
     * @param pendingIntent
     * @param sound
     * @param vibrate
     * @param lights
     */
    public void sendMoreLineNotification(String ticker, String title, String content, int smallIcon, PendingIntent pendingIntent, boolean sound, boolean vibrate, boolean lights) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            Toast.makeText(mContext, "您的系统版本低于Android 4.1.2，不支持！", Toast.LENGTH_SHORT).show();
        }
        //通用设置
        sample(ticker, title, content, smallIcon, pendingIntent, sound, vibrate, lights);
        //实例化多行文本的通知
        Notification notification = new NotificationCompat.BigTextStyle(mBuilder).bigText(content).build();
        //发送通知
        send(notification);
    }

    /**
     * 大图通知
     *
     * @param ticker
     * @param title
     * @param content
     * @param smallIcon
     * @param pendingIntent
     * @param sound
     * @param vibrate
     * @param lights
     */
    public void sendBigPicNotification(String ticker, String title, String content, int smallIcon, PendingIntent pendingIntent, boolean sound, boolean vibrate, boolean lights) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            Toast.makeText(mContext, "您的系统版本低于Android 4.1.2，不支持！", Toast.LENGTH_SHORT).show();
        }
        //通用设置
        sample(ticker, title, content, smallIcon, pendingIntent, sound, vibrate, lights);
        //大图
        Bitmap bitPicture = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
        //图标
        Bitmap bigLargeIcon = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.android_bigicon);

        //创建Notification
        Notification notification = new NotificationCompat.BigPictureStyle(mBuilder).bigLargeIcon(bigLargeIcon).bigPicture(bitPicture).build();
        //发送通知
        send(notification);
    }

    /**
     * 列表通知
     *
     * @param ticker
     * @param title
     * @param content
     * @param smallIcon
     * @param pendingIntent
     * @param sound
     * @param vibrate
     * @param lights
     */
    public void sendListNotification(String ticker, String title, String content, int smallIcon, PendingIntent pendingIntent, ArrayList<String> contents, boolean sound, boolean vibrate, boolean lights) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            Toast.makeText(mContext, "您的系统版本低于Android 4.1.2，不支持！", Toast.LENGTH_SHORT).show();
        }
        //通用设置
        sample(ticker, title, content, smallIcon, pendingIntent, sound, vibrate, lights);

        NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle(mBuilder);

        //添加内容
        for (String s : contents) {
            style.addLine(s);
        }

        style.setSummaryText(contents.size() + "条消息");
        style.setBigContentTitle(title);
        Notification notification = style.build();
        //发送通知
        send(notification);
    }

    /**
     * 双折叠按钮通知
     *
     * @param ticker
     * @param title
     * @param content
     * @param smallIcon
     * @param pendingIntent
     * @param leftIcon
     * @param leftText
     * @param leftPI
     * @param rightIcon
     * @param rightText
     * @param rightPI
     * @param sound
     * @param vibrate
     * @param lights
     */
    public void sendActionNotification(String ticker, String title, String content, int smallIcon, PendingIntent pendingIntent,
                                       int leftIcon, String leftText, PendingIntent leftPI,
                                       int rightIcon, String rightText, PendingIntent rightPI,
                                       boolean sound, boolean vibrate, boolean lights) {

        //通用设置
        sample(ticker, title, content, smallIcon, pendingIntent, sound, vibrate, lights);
        mBuilder.addAction(leftIcon, leftText, leftPI);
        mBuilder.addAction(rightIcon, rightText, rightPI);
        Notification notification = mBuilder.build();
        //发送通知
        send(notification);
    }

    /**
     * 带进度条的通知栏
     *
     * @param ticker
     * @param title
     * @param content
     * @param smallIcon
     * @param pendingIntent
     * @param sound
     * @param vibrate
     * @param lights
     */
    public void sendProgressNotification(String ticker, String title, String content, int smallIcon, PendingIntent pendingIntent,
                                         boolean sound, boolean vibrate, boolean lights) {

        //通用设置
        sample(ticker, title, content, smallIcon, pendingIntent, sound, vibrate, lights);

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i += 10) {

                    mBuilder.setProgress(100, i, false);
                    //发送通知
                    send(mBuilder.build());

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                //下载完成
                mBuilder.setContentText("下载完成").setProgress(100, 100, false);
                //发送通知
                send(mBuilder.build());

            }
        }).start();

    }

    /**
     * 自定义通知视图
     *
     * @param ticker
     * @param title
     * @param content
     * @param smallIcon
     * @param pendingIntent
     * @param contentView
     * @param bigContentView
     * @param sound
     * @param vibrate
     * @param lights
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void sendCustomerNotification(String ticker, String title, String content, int smallIcon, PendingIntent pendingIntent,
                                         RemoteViews contentView, RemoteViews bigContentView, boolean sound, boolean vibrate, boolean lights) {
        //通用设置
        sample(ticker, title, content, smallIcon, pendingIntent, sound, vibrate, lights);
        //实例化Notification
        Notification notification = mBuilder.build();
        //在api大于等于16的情况下，如果视图超过一定范围，可以转变成bigContentView
        notification.bigContentView=bigContentView;
        notification.contentView=contentView;
        //发送通知
        send(notification);
    }


    /**
     * 发送通知
     *
     * @param notification
     */
    private void send(Notification notification) {
        //每个ID会区分每一条通知
        mManager.notify(notificationId, notification);
    }


}
