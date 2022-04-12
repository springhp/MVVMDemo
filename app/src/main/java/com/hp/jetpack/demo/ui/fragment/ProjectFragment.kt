package com.hp.jetpack.demo.ui.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.base.activity.BaseFragment
import com.hp.jetpack.demo.base.viewmodel.BaseViewModel
import com.hp.jetpack.demo.databinding.FragmentLoginBinding
import kotlinx.android.synthetic.main.fragment_project.*


class ProjectFragment : BaseFragment<BaseViewModel, FragmentLoginBinding>() {
    override fun layoutId() = R.layout.fragment_project

    val tabs = arrayOf("关注", "推荐", "最新")
    override fun initView(savedInstanceState: Bundle?) {
        viewpage.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
        viewpage.adapter = object :
            FragmentStateAdapter(childFragmentManager, lifecycle) {
            override fun createFragment(position: Int): Fragment {
                //FragmentStateAdapter内部自己会管理已实例化的fragment对象。
                // 所以不需要考虑复用的问题
                return LoginFragment()
            }

            override fun getItemCount(): Int {
                return tabs.size
            }
        }

        val mediator = TabLayoutMediator(
            tabLayout, viewpage
        ) { tab, position -> //这里可以自定义TabView
            val tabView = TextView(context)
            val states = arrayOfNulls<IntArray>(2)
            states[0] = intArrayOf(android.R.attr.state_selected)
            states[1] = intArrayOf()
            // val colors = intArrayOf(activeColor, normalColor)
//            val colorStateList = ColorStateList(states, colors)
            tabView.text = tabs[position]
            // tabView.textSize = normalSize
//            tabView.setTextColor(colorStateList)
            tab.customView = tabView
        }
        //要执行这一句才是真正将两者绑定起来
        //要执行这一句才是真正将两者绑定起来
        mediator.attach()
    }


}