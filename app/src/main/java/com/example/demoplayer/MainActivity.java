package com.example.demoplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.demoplayer.Utils.ApplicationContext;
import com.example.demoplayer.Utils.ImageUtils;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ApplicationContext.setManager(getSupportFragmentManager());
        ApplicationContext.setContext(getApplicationContext());
        ImageUtils.setHashMap();
        replaceFragment(new ItemFragment());
        Log.d(TAG,"after onCreate ");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ApplicationContext.getManager().popBackStack();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();   // 开启一个事务
        transaction.replace(R.id.fragment_item_list, fragment);
        transaction.commit();
    }
}