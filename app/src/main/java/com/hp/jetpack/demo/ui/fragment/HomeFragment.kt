package com.hp.jetpack.demo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.base.activity.BaseFragment
import com.hp.jetpack.demo.data.bean.BannerResponse
import com.hp.jetpack.demo.databinding.FragmentHomeBinding
import com.hp.jetpack.demo.ext.*
import com.hp.jetpack.demo.model.HomeViewModel
import com.hp.jetpack.demo.ui.adapter.AriticleAdapter
import com.hp.jetpack.demo.weight.banner.HomeBannerAdapter
import com.hp.jetpack.demo.weight.banner.HomeBannerViewHolder
import com.hp.jetpack.demo.weight.recyclerview.DefineLoadMoreView
import com.hp.jetpack.demo.weight.recyclerview.SpaceItemDecoration
import com.kingja.loadsir.core.LoadService
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import com.zhpan.bannerview.BannerViewPager
import kotlinx.android.synthetic.main.include_list.*
import kotlinx.android.synthetic.main.include_recyclerview.*
import kotlinx.android.synthetic.main.include_toolbar.*


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    //适配器
    private val articleAdapter: AriticleAdapter by lazy { AriticleAdapter(arrayListOf(), true) }

    //界面状态管理者
    private lateinit var loadSir: LoadService<Any>

    override fun layoutId() = R.layout.fragment_home

    //recyclerview的底部加载view 因为在首页要动态改变他的颜色，所以加了他这个字段
    private lateinit var footView: DefineLoadMoreView

    override fun initView(savedInstanceState: Bundle?) {
        immersionBar {
        }

        toolbar.run {
            title = "首页"
            inflateMenu(R.menu.home_menu)

            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.home_search -> {
                        nav().navigate(R.id.qr_code_Fragment, Bundle().apply {
                            putString("test", "test2")
                        })
                    }
                }
                true
            }
//            mActivity.setSupportActionBar(this)
        }
        loadSir = loadServiceInit(swipeRefresh) {
            loadSir.showLoading()
            mViewModel.getBannerData()
            mViewModel.getHomeData(true)
        }

        recyclerView.init(LinearLayoutManager(context), articleAdapter).let {
            //因为首页要添加轮播图，所以我设置了firstNeedTop字段为false,即第一条数据不需要设置间距
            it.addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f), false))
            footView = it.initFooter(SwipeRecyclerView.LoadMoreListener {
                mViewModel.getHomeData(false)
            })
            //初始化FloatingActionButton
            it.initFloatBtn(floatBtn)
        }

    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        loadSir.showLoading()
        mViewModel.getBannerData()
        mViewModel.getHomeData(true)
    }

    override fun createObserver() {
        super.createObserver()
        mViewModel.run {
            homeDataState.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                //设值 新写了个拓展函数，搞死了这个恶心的重复代码
                loadListData(it, articleAdapter, loadSir, recyclerView, swipeRefresh)
            })

            bannerData.observe(viewLifecycleOwner, androidx.lifecycle.Observer { resultState ->
                parseState(resultState, { data ->
                    if (recyclerView.headerCount == 0) {
                        var headView =
                            LayoutInflater.from(context).inflate(R.layout.include_banner, null)
                                .apply {
                                    findViewById<BannerViewPager<BannerResponse, HomeBannerViewHolder>>(
                                        R.id.banner_view
                                    ).apply {
                                        adapter = HomeBannerAdapter()
                                        setLifecycleRegistry(lifecycle)
                                        setOnPageClickListener {
                                            nav().navigateAction(
                                                R.id.action_to_webFragment,
                                                Bundle().apply {
                                                    putParcelable(
                                                        "bannerdata",
                                                        data[it]
                                                    )
                                                })
                                        }
                                        create(data)
                                    }
                                }
                        recyclerView.addHeaderView(headView)
                        recyclerView.scrollToPosition(0)
                    }
                })
            })
        }
    }


}