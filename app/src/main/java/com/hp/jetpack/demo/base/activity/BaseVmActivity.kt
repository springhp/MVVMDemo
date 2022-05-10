package com.hp.jetpack.demo.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.blankj.utilcode.util.LogUtils
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.base.viewmodel.BaseViewModel
import com.hp.jetpack.demo.ext.getVmClazz

abstract class BaseVmActivity<VM : BaseViewModel> : AppCompatActivity() {
    var isBackground = false
    /**
     * 是否需要使用DataBinding 供子类BaseVmDbActivity修改，用户请慎动
     */
    private var isUserDb = false

    lateinit var mViewModel: VM

    abstract fun layoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isUserDb) {
            setContentView(layoutId())
        } else {
            initDataBind()
        }
        init(savedInstanceState)
    }

    open fun initDataBind() {}

    private fun init(savedInstanceState: Bundle?) {
        mViewModel = createViewModel()
        //TODO 可以考虑在activity里面使用loading
        registerUiChange()
        initView(savedInstanceState)
        createObserver()
        //网络监控 在MainActivity监听
    }

    /**
     * 创建LiveData数据观察者
     */
    abstract fun createObserver()

    abstract fun initView(savedInstanceState: Bundle?)

    private fun registerUiChange(){
        mViewModel.loadingChange.showDialog.observe(this) {
            showLoading(it)
        }
        mViewModel.loadingChange.dismissDialog.observe(this){
            dismissLoading()
        }
    }

    abstract fun showLoading(message: String = "请求中...")

    abstract fun dismissLoading()

    /**
     * 创建ViewModel
     */
    private fun createViewModel(): VM {
        return ViewModelProvider(this).get(getVmClazz(this))
    }

    fun userDataBinding(isUserDb: Boolean) {
        this.isUserDb = isUserDb
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        isBackground = true
        LogUtils.e("onUserLeaveHint",isBackground)
    }

    override fun onResume() {
        super.onResume()
        if (isBackground){
            isBackground = false
            LogUtils.e("onResume",isBackground)
            LogUtils.e("从后台进入前台，打开广告页面")
            MaterialDialog(this).show {
                title(R.string.clear_cashe)
                message(text = "模拟广告")
                positiveButton(R.string.btn_ok) {
                    LogUtils.e("确定")
                }
                cancelOnTouchOutside(false)
                negativeButton(R.string.btn_cancel) {
                    LogUtils.e("取消")
                }
                lifecycleOwner(this@BaseVmActivity)
            }
        }
    }
}