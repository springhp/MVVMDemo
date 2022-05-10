package com.hp.jetpack.demo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.gyf.immersionbar.ktx.immersionBar
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.base.activity.BaseFragment
import com.hp.jetpack.demo.data.bean.BannerResponse
import com.hp.jetpack.demo.databinding.FragmentHomeBinding
import com.hp.jetpack.demo.ext.*
import com.hp.jetpack.demo.model.HomeViewModel
import com.hp.jetpack.demo.ui.adapter.AriticleAdapter
import com.hp.jetpack.demo.weight.recyclerview.SpaceItemDecoration
import com.kingja.loadsir.core.LoadService
import com.youth.banner.Banner
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    //适配器
    private val articleAdapter: AriticleAdapter by lazy { AriticleAdapter(arrayListOf(), true) }

    //界面状态管理者
    private lateinit var loadSir: LoadService<Any>

    override fun layoutId() = R.layout.fragment_home

    override fun initView(savedInstanceState: Bundle?) {
        mDataBinding.toolbar.run {
            title = "首页"
            inflateMenu(R.menu.home_menu)

            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.home_search -> {
                        nav().navigateAction(R.id.search_fragment)
                    }
                }
                true
            }
        }

        loadSir = loadServiceInit(mDataBinding.smartRefreshLayout) {
            loadSir.showLoading()
            mViewModel.getBannerData()
            mViewModel.getHomeData(true)
        }

        mDataBinding.smartRefreshLayout.setOnRefreshListener {
            mViewModel.getHomeData(true)
        }

        mDataBinding.smartRefreshLayout.setOnLoadMoreListener {
            mViewModel.getHomeData(false)
        }

        mDataBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = articleAdapter
            addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f), false))
        }

        articleAdapter.run {
            setOnItemClickListener { _, _, position ->
                position.toString().logE()
                nav().navigateAction(R.id.action_to_webFragment, Bundle().apply {
                    putParcelable(
                        "ariticleData",
                        articleAdapter.data[position]
                        //1为head
                    )
                })
            }


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
            homeDataState.observe(viewLifecycleOwner) {
                //设值 新写了个拓展函数，搞死了这个恶心的重复代码
                loadListData(
                    it,
                    articleAdapter,
                    loadSir,
                    mDataBinding.recyclerView,
                    mDataBinding.smartRefreshLayout
                )
            }

            bannerData.observe(viewLifecycleOwner) { resultState ->
                parseState(resultState, { data ->
                    if (articleAdapter.headerLayoutCount == 0) {
                        val headView =
                            LayoutInflater.from(context).inflate(R.layout.include_banner, null)

                        var banner: Banner<BannerResponse, BannerImageAdapter<BannerResponse>> =
                            headView.findViewById(R.id.banner_view)

                        banner.setAdapter(object : BannerImageAdapter<BannerResponse>(data) {
                            override fun onBindView(
                                holder: BannerImageHolder,
                                data: BannerResponse,
                                position: Int,
                                size: Int
                            ) {
                                //图片加载自己实现
                                Glide.with(holder.itemView)
                                    .load(data.imagePath)
                                    .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
                                    .into(holder.imageView)
                            }
                        }).addBannerLifecycleObserver(this@HomeFragment).indicator =
                            CircleIndicator(mActivity)
                        articleAdapter.addHeaderView(headView)
                        mDataBinding.recyclerView.scrollToPosition(0)
                    }
                })
            }
        }
    }


}