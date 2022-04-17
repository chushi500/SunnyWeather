package com.android.sunnyweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class SunnyWeatherApplication : Application() {
    companion object {
        const val TOKEN = "TAkhjf8d1nlSlspN" //令牌值
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        //使用MVVM这种分层架构设计，从ViewModel层开始就不再持有Activity的引用
        context = applicationContext //全局获取Context
    }
}