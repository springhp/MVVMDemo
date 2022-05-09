package com.hp.jetpack.demo.ui.fragment

import android.os.Bundle
import com.blankj.utilcode.util.ToastUtils
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.base.activity.BaseFragment
import com.hp.jetpack.demo.base.viewmodel.BaseViewModel
import com.hp.jetpack.demo.databinding.FragmentTestBinding
import com.hp.jetpack.demo.model.ProjectViewModel

class TestFragment : BaseFragment<ProjectViewModel, FragmentTestBinding>() {

    companion object{
        fun newInstance(cid: Int): TestFragment {
            val args = Bundle()

            val fragment = TestFragment()
            fragment.arguments = args
            args.putInt("cid", cid)
            return fragment
        }
    }

    override fun layoutId(): Int = R.layout.fragment_test

    override fun initView(savedInstanceState: Bundle?) {
        arguments?.let {
            var msg = it.getInt("cid")
            mDataBinding.tvMessage.text = msg.toString()
        }
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        mViewModel.getProjectList(true,1)
    }

    override fun createObserver() {
        super.createObserver()
        mViewModel.projectListState.observe(viewLifecycleOwner){
            ToastUtils.showLong(it.message)
        }
    }
}