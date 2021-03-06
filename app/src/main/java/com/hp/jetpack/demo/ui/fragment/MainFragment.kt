package com.hp.jetpack.demo.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.base.activity.BaseFragment
import com.hp.jetpack.demo.base.viewmodel.BaseViewModel
import com.hp.jetpack.demo.databinding.FragmentMainBinding

class MainFragment : BaseFragment<BaseViewModel, FragmentMainBinding>() {

    override fun layoutId() = R.layout.fragment_main

    override fun initView(savedInstanceState: Bundle?) {

        mDataBinding.mainViewpager.apply {
            isUserInputEnabled = false
            offscreenPageLimit = 4
            adapter = object : FragmentStateAdapter(this@MainFragment) {
                override fun createFragment(position: Int): Fragment {
                    return when (position) {
                        0 -> {
                            HomeFragment()
                        }
                        1 -> {
                            ProjectFragment()
                        }
                        2 -> {
                            WxArticleFragment()
                        }
                        else -> {
                            MeFragment()
                        }
                    }
                }

                override fun getItemCount() = 5
            }
        }

        mDataBinding.mainBottom.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_main -> {
                    mDataBinding.mainViewpager.setCurrentItem(0, false)
                }
                R.id.menu_project -> {
                    mDataBinding.mainViewpager.setCurrentItem(1, false)
                }
                R.id.menu_public -> {
                    mDataBinding.mainViewpager.setCurrentItem(2, false)
                }
                else -> {
                    mDataBinding.mainViewpager.setCurrentItem(3, false)
                }
            }
            return@setOnItemSelectedListener true
        }

        mDataBinding.mainBottom.selectedItemId = R.id.menu_main

    }
}