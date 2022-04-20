package com.hp.jetpack.demo.ui.activity

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.blankj.utilcode.util.LogUtils
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.base.activity.BaseActivity
import com.hp.jetpack.demo.base.viewmodel.BaseViewModel
import com.hp.jetpack.demo.databinding.ActivityWorkManagerBinding
import java.util.concurrent.TimeUnit

class WorkManagerActivity : BaseActivity<BaseViewModel, ActivityWorkManagerBinding>() {
    override fun layoutId(): Int = R.layout.activity_work_manager

    override fun createObserver() {

    }

    override fun initView(savedInstanceState: Bundle?) {
        mDataBinding.toolbar.run {
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                finish()
            }
        }

        var constraints = Constraints.Builder().apply {
            setRequiredNetworkType(NetworkType.CONNECTED)   //网络连接状态
            setRequiresBatteryNotLow(true)  //不在电量不足的情况下
            setRequiresStorageNotLow(true)  //不在存储不足的情况下
            //setRequiresDeviceIdle(true)     //在待机情况下执行 需要 API 23
            setRequiresCharging(true)       //充电
        }.build()

        var oneTimeWorkRequest = OneTimeWorkRequest.Builder(TestWorker::class.java)
            .setConstraints(constraints).build()

        var oneTimeWorkRequest2 = OneTimeWorkRequest.Builder(Test2Worker::class.java)
            .setConstraints(constraints).build()

        //WorkManager.getInstance(this).enqueue(oneTimeWorkRequest)

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(oneTimeWorkRequest.id).observe(this){
            it?.run {
                LogUtils.e(this.state)
            }
        }

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(oneTimeWorkRequest2.id).observe(this){
            it?.run {
                LogUtils.e(this.state)
            }
        }

        // 间隔最小15分钟
        var periodicWorkRequest = PeriodicWorkRequest.Builder(TestWorker::class.java,15,TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()
        //WorkManager.getInstance(this).enqueue(periodicWorkRequest)

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(periodicWorkRequest.id).observe(this){
            it?.run {
                LogUtils.e(this.state)
            }
        }
        //链式执行
        WorkManager.getInstance(this).beginWith(oneTimeWorkRequest).then(oneTimeWorkRequest2).enqueue()
    }

    class TestWorker(context: Context, workerParams: WorkerParameters) :
        Worker(context, workerParams) {
        override fun doWork(): Result {
            LogUtils.e("执行了TestWorker")
            return Result.success()
        }
    }

    class Test2Worker(context: Context, workerParams: WorkerParameters) :
        Worker(context, workerParams) {
        override fun doWork(): Result {
            LogUtils.e("执行了Test2Worker")
            return Result.success()
        }
    }
}