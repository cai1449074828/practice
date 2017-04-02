package com.blq.zzc.practice.ui.Application;

import android.content.Context;

import com.baidu.apistore.sdk.ApiStoreSDK;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by Administrator on 2016/9/3.
 */
public class MyApplication extends android.app.Application{
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        ApiStoreSDK.init(this, "592e46b62cfe201c68bf7d9f18db11ee");
        Fresco.initialize(this);
        context=this;
    }
    public static Context getContext()
    {

        return context;
    }
}
