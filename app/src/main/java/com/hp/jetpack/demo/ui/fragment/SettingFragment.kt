package com.hp.jetpack.demo.ui.fragment

import android.os.Bundle
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.afollestad.materialdialogs.list.customListAdapter
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.ToastUtils
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.base.activity.BaseFragment
import com.hp.jetpack.demo.data.bean.result.EnterpriseBean
import com.hp.jetpack.demo.databinding.FragmentSettingBinding
import com.hp.jetpack.demo.model.SettingViewModel
import com.hp.jetpack.demo.ui.adapter.EnterpriseAdapter
import com.hp.jetpack.demo.util.MySpUtils

class SettingFragment : BaseFragment<SettingViewModel, FragmentSettingBinding>() {
    override fun layoutId(): Int = R.layout.fragment_setting

    override fun initView(savedInstanceState: Bundle?) {
        mDataBinding.llEnterprise.setOnClickListener {
            mViewModel.enterpriseBeanList.value ?: mViewModel.getEnterprise()
            mViewModel.enterpriseBeanList.value?.let {
                if (it.data.isEmpty()) {
                    mViewModel.getEnterprise()
                }else{
                    showAdapterMaterialDialog(it.data.toMutableList())
                }
            }
        }

        mDataBinding.tvEnterprise.text = MySpUtils.enterpriseName

        mDataBinding.tvVersion.text = AppUtils.getAppVersionName()
    }

    override fun createObserver() {
        super.createObserver()

        mViewModel.enterpriseBeanList.observe(viewLifecycleOwner) {
            if (it.isSuccess()) {
                if (it.data.isEmpty()) {
                    ToastUtils.showShort("没有数据")
                } else {
                    showAdapterMaterialDialog(it.data.toMutableList())
                }
            } else {
                ToastUtils.showShort(it.message)
            }
        }
    }

    private fun showAdapterMaterialDialog(data: MutableList<EnterpriseBean>) {
        var adapter = EnterpriseAdapter(data)

        var dialog = MaterialDialog(mActivity, BottomSheet(LayoutMode.WRAP_CONTENT)).show {
            customListAdapter(adapter)
            lifecycleOwner(this@SettingFragment)
        }

        adapter.setOnItemClickListener() { _, _, position ->
            var bean = data[position]
            MySpUtils.enterpriseID = bean.id
            MySpUtils.enterpriseName = bean.text
            mDataBinding.tvEnterprise.text = bean.text
            dialog.dismiss()
        }

    }
}