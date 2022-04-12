package com.hp.jetpack.demo.base.activity

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.databinding.ViewDataBinding
import com.hp.jetpack.demo.base.fragment.BaseVmDbFragment
import com.hp.jetpack.demo.base.viewmodel.BaseViewModel
import com.hp.jetpack.demo.ext.dismissLoadingExt
import com.hp.jetpack.demo.ext.showLoadingExt

abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding> : BaseVmDbFragment<VM, DB>() {
    /**
     * 打开等待框
     */
    override fun showLoading(message: String) {
        showLoadingExt(message)
    }

    /**
     * 关闭等待框
     */
    override fun dismissLoading() {
        dismissLoadingExt()
    }

    override fun onPause() {
        super.onPause()
        //隐藏键盘
        activity?.let { act ->
            val view = act.currentFocus
            view?.let {
                val inputMethodManager =
                    act.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(
                    view.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        }
    }

    override fun initData() {
    }

    override fun lazyLoadData() {
    }

    override fun createObserver() {
    }
}