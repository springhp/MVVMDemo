package com.hp.jetpack.demo.ui.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import com.blankj.utilcode.util.LogUtils

class MyAppWidgetProvider : AppWidgetProvider() {
    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
        LogUtils.e("onDeleted")
    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
        LogUtils.e("onDisabled")
    }

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        LogUtils.e("onEnabled")
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        LogUtils.e("onReceive")
    }

    override fun onRestored(context: Context?, oldWidgetIds: IntArray?, newWidgetIds: IntArray?) {
        super.onRestored(context, oldWidgetIds, newWidgetIds)
        LogUtils.e("onRestored")
    }

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        LogUtils.e("onUpdate")
    }
}