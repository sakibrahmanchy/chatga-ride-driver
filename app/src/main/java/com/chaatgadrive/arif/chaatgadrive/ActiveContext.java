package com.chaatgadrive.arif.chaatgadrive;

import android.content.Context;

public class ActiveContext {
    public static Context activeContext=null;
    public ActiveContext(Context context){
        activeContext=context;
    }
}
