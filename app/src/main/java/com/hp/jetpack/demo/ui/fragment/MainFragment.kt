package com.hp.jetpack.demo.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.base.activity.BaseFragment
import com.hp.jetpack.demo.base.viewmodel.BaseViewModel
import com.hp.jetpack.demo.databinding.FragmentMainBinding
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment<BaseViewModel, FragmentMainBinding>() {

    override fun layoutId() = R.layout.fragment_main

    override fun initView(savedInstanceState: Bundle?) {

        mainViewpager.apply {
            isUserInputEnabled = false
            offscreenPageLimit = 3
            adapter = object : FragmentStateAdapter(this@MainFragment) {
                override fun createFragment(position: Int): Fragment {
                    return when (position) {
                        0 -> {
                            DataFragment()
                        }
                        1 -> {
                            VisitorFragment()
                        }
                        else -> {
                            SettingFragment()
                        }
                    }
                }

                override fun getItemCount() = 3
            }
        }

        mainBottom.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_main -> {
                    mainViewpager.setCurrentItem(0, false)
                }
                R.id.menu_project -> {
                    mainViewpager.setCurrentItem(1, false)
                }
                else -> {
                    mainViewpager.setCurrentItem(2, false)
                }
            }
            return@setOnItemSelectedListener true
        }

    }
}