package com.hjg.huaweiautoinstall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void open(View view) {

        if (AutoInstallService.isStart()) {
            Toast.makeText(this, "已经打开", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent2 = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent2);
        }


    }

    public void close(View view) {
        Intent intent2 = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        startActivity(intent2);
    }


}