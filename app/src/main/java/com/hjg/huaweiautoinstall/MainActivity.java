package com.hjg.huaweiautoinstall;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume() {
        super.onResume();
        findViewById(R.id.open).setEnabled(AutoInstallService.isStart() ? false : true);
        findViewById(R.id.systemAlert).setEnabled(!Settings.canDrawOverlays(this) ? true : false);
    }

    //打开悬浮窗权限
    public void openSystemAlert(View view) {
        startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getPackageName())), 100);
    }

    /**
     * 开启服务
     *
     * @param view
     */
    public void startService(View view) {
        if (Settings.canDrawOverlays(this)) {//有权限就打开一个悬浮窗
            startService(new Intent(getApplicationContext(), FloatingWindowService.class));
            findViewById(R.id.openService).setEnabled(false);
        } else {
            Toast.makeText(this, "请先打开悬浮窗权限", Toast.LENGTH_SHORT).show();
        }
    }


    public void open(View view) {

        Intent intent2 = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        startActivity(intent2);

    }

    public void close(View view) {
        Intent intent2 = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        startActivity(intent2);
    }

}