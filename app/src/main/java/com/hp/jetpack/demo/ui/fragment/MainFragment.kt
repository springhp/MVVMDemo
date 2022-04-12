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
            offscreenPageLimit = 5
            adapter = object : FragmentStateAdapter(this@MainFragment) {
                override fun createFragment(position: Int): Fragment {
                    when (position) {
                        0 -> {
                            return HomeFragment()
                        }
                        1 -> {
                            return ProjectFragment()
                        }
                        2 -> {
                            return RegisterFragment()
                        }
                        3 -> {
                            return HomeFragment()
                        }
                        4 -> {
                            return MeFragment()
                        }
                        else -> {
                            return MeFragment()
                        }
                    }
                }

                override fun getItemCount() = 5
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
                R.id.menu_system -> {
                    mainViewpager.setCurrentItem(2, false)
                }
                R.id.menu_public -> {
                    mainViewpager.setCurrentItem(3, false)
                }
                else -> {
                    mainViewpager.setCurrentItem(4, false)
                }
            }
            return@setOnItemSelectedListener true
        }

    }
}