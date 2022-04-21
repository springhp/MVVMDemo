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

    //recyclerview的底部加载view 因为在首页要动态改变他的颜色，所以加了他这个字段
//    private lateinit var footView: DefineLoadMoreView

    override fun initView(savedInstanceState: Bundle?) {
        immersionBar {
            statusBarColor(R.color.colorPrimary)
            navigationBarColor(R.color.colorPrimary)
        }
        mDataBinding.toolbar.run {
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
        }

        loadSir = loadServiceInit(mDataBinding.swipeRefresh) {
            loadSir.showLoading()
            mViewModel.getBannerData()
            mViewModel.getHomeData(true)
        }

        mDataBinding.recyclerView.init(LinearLayoutManager(context), articleAdapter).let {
            //因为首页要添加轮播图，所以我设置了firstNeedTop字段为false,即第一条数据不需要设置间距
            it.addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f), false))
//            footView = it.initFooter {
//                mViewModel.getHomeData(false)
//            }
            //初始化FloatingActionButton
            //it.initFloatBtn(floatBtn)
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
                    mDataBinding.swipeRefresh
                )
            }

            bannerData.observe(viewLifecycleOwner) { resultState ->
                parseState(resultState, { data ->
                    if (mDataBinding.recyclerView.headerCount == 0) {
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
                        mDataBinding.recyclerView.addHeaderView(headView)
                        mDataBinding.recyclerView.scrollToPosition(0)
                    }
                })
            }
        }
    }


}