package com.hjg.huaweiautoinstall;


import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.GestureDescription;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;


import java.util.List;


/**
 * 电视台观看视频的时候，是不会有点阅号的。
 * 阅读文章的时候要特别关注，是不是会有。
 */
public class AutoInstallService extends AccessibilityService {
    public static AutoInstallService mService;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    AccessibilityNodeInfo accessibilityNodeInfo = (AccessibilityNodeInfo) msg.obj;
                    accessibilityNodeInfo.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
                    accessibilityNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    break;
            }
        }
    };


    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        mService = this;
    }

    AccessibilityEvent accessibilityEvent;
    String packageName = "";

    //实现辅助功能
    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        this.accessibilityEvent = accessibilityEvent;


        Log.d("autoservice", accessibilityEvent.getPackageName() + "\n" + accessibilityEvent.getClassName());
        packageName = accessibilityEvent.getPackageName().toString();

        if (packageName.equals("com.android.packageinstaller")) {
            //继续安装
            List<AccessibilityNodeInfo> list = this.getRootInActiveWindow().findAccessibilityNodeInfosByViewId("android:id/button1");
            for (int i = 0; i < list.size(); i++) {
                AccessibilityNodeInfo accessibilityNodeInfo = list.get(i);
                if (accessibilityNodeInfo.getText().toString().equals("继续安装")) {
                    Message message = mHandler.obtainMessage();
                    message.obj = accessibilityNodeInfo;
                    message.what = 1;
                    mHandler.sendMessageDelayed(message, 1000);
                    Log.d("autoservice", "找到,点击继续安装");
                }
            }

        }

        if (packageName.equals("com.huawei.appmarket")) {
            //继续安装
            List<AccessibilityNodeInfo> list = this.getRootInActiveWindow().findAccessibilityNodeInfosByViewId("com.huawei.appmarket:id/hidden_card_install_button_continue");
            for (int i = 0; i < list.size(); i++) {
                AccessibilityNodeInfo accessibilityNodeInfo = list.get(i);
                if (accessibilityNodeInfo.getText().toString().equals("继续安装")) {
                    Message message = mHandler.obtainMessage();
                    message.obj = accessibilityNodeInfo;
                    message.what = 1;
                    mHandler.sendMessageDelayed(message, 1000);
                    Log.d("autoservice", "找到市场里面的继续安装,点击继续安装");
                }
            }

            //打开
            List<AccessibilityNodeInfo> list2 = this.getRootInActiveWindow().findAccessibilityNodeInfosByViewId("com.huawei.appmarket:id/hiapp_launch_button");
            for (int i = 0; i < list2.size(); i++) {
                AccessibilityNodeInfo accessibilityNodeInfo = list2.get(i);
                if (accessibilityNodeInfo.getText().toString().equals("打开")) {
                    Message message = mHandler.obtainMessage();
                    message.obj = accessibilityNodeInfo;
                    message.what = 1;
                    mHandler.sendMessageDelayed(message, 1000);
                }
            }


        }

    }


    @Override
    public void onInterrupt() {
        mHandler.removeCallbacksAndMessages(null);
        mService = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        mService = null;
    }


    /**
     * 辅助功能是否启动
     */
    public static boolean isStart() {
        return mService != null;
    }


    /**
     * 点击指定位置
     * 注意7.0以上的手机才有此方法，请确保运行在7.0手机上  [614,2402][1107,2499]
     */
    @RequiresApi(24)
    public void dispatchGestureClick(int x, int y) {
        Path path = new Path();
        path.moveTo(x, y);
        dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription
                (path, 0, 100)).build(), null, null);
    }


}