package com.example.demoplayer.Utils;

import android.content.Context;

import androidx.fragment.app.FragmentManager;

public class ApplicationContext {
    private static Context mContext;
    private static FragmentManager manager;

    public static FragmentManager getManager() {
        return manager;
    }

    public static void setManager(FragmentManager manager) {
        ApplicationContext.manager = manager;
    }

    public static Context getContext() {
        return mContext;
    }

    public static void setContext(Context context) {
        mContext = context;
    }
}
