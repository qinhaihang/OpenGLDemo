package com.example.opengldemo.openglutils

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.util.Log

fun getOpenGLVersion(activity: Activity): Int{
    val activityManager = activity.getSystemService(Context.ACTIVITY_SERVICE)
    var version = 0
    if(activityManager is ActivityManager){
        Log.d("","ActivityManager")
        return activityManager.deviceConfigurationInfo.reqGlEsVersion
    }
    return version
}