package com.hp.jetpack.demo.ext

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.blankj.utilcode.util.ConvertUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.app.appContext
import com.hp.jetpack.demo.data.bean.ProjectTree
import com.hp.jetpack.demo.data.bean.WxArticleTree
import com.hp.jetpack.demo.network.state.ListDataUiState
import com.hp.jetpack.demo.weight.loadCallBack.EmptyCallback
import com.hp.jetpack.demo.weight.loadCallBack.ErrorCallback
import com.hp.jetpack.demo.weight.loadCallBack.LoadingCallback
import com.hp.jetpack.demo.weight.recyclerview.DefineLoadMoreView
import com.hp.jetpack.demo.weight.viewpage.ScaleTransitionPagerTitleView
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView

fun loadServiceInit(view: View, callback: () -> Unit): LoadService<Any> {
    val loadSir = LoadSir.getDefault().register(view) {
        //??????????????????????????????
        callback.invoke()
    }
    loadSir.showSuccess()
//    SettingUtil.setLoadingColor(SettingUtil.getColor(appContext), loadsir)
    return loadSir
}

/**
 * ???????????????
 */
fun LoadService<*>.showLoading() {
    this.showCallback(LoadingCallback::class.java)
}

//??????SwipeRecyclerView
fun SwipeRecyclerView.init(
    layoutManger: RecyclerView.LayoutManager,
    bindAdapter: RecyclerView.Adapter<*>,
    isScroll: Boolean = true
): SwipeRecyclerView {
    layoutManager = layoutManger
    setHasFixedSize(true)
    adapter = bindAdapter
    isNestedScrollingEnabled = isScroll
    return this
}

fun SwipeRecyclerView.initFooter(loadMoreListener: SwipeRecyclerView.LoadMoreListener): DefineLoadMoreView {
    val footerView = DefineLoadMoreView(appContext)
    //?????????????????????
//    footerView.setLoadViewColor(SettingUtil.getOneColorStateList(appContext))
    //????????????????????????
    footerView.setmLoadMoreListener(SwipeRecyclerView.LoadMoreListener {
        footerView.onLoading()
        loadMoreListener.onLoadMore()
    })
    this.run {
        //????????????????????????
        addFooterView(footerView)
        setLoadMoreView(footerView)
        //????????????????????????
        setLoadMoreListener(loadMoreListener)
    }
    return footerView
}

fun RecyclerView.initFloatBtn(floatBtn: FloatingActionButton) {
    //??????recyclerview?????????????????????????????????????????????????????????????????????
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        @SuppressLint("RestrictedApi")
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (!canScrollVertically(-1)) {
                floatBtn.visibility = View.INVISIBLE
            }
        }
    })
//    floatBtn.backgroundTintList = SettingUtil.getOneColorStateList(appContext)
    floatBtn.setOnClickListener {
        val layoutManager = layoutManager as LinearLayoutManager
        //????????????recyclerview ?????????????????????????????????????????????40????????????????????????????????????????????????????????????????????????
        if (layoutManager.findLastVisibleItemPosition() >= 40) {
            scrollToPosition(0)//?????????????????????????????????(??????)
        } else {
            smoothScrollToPosition(0)//??????????????????????????????(?????????)
        }
    }
}

/**
 * ??????????????????
 */
fun <T> loadListData(
    data: ListDataUiState<T>,
    baseQuickAdapter: BaseQuickAdapter<T, *>,
    loadService: LoadService<*>,
    recyclerView: RecyclerView,
    smartRefreshLayout: SmartRefreshLayout
) {
    if (data.isRefresh) {
        smartRefreshLayout.finishRefresh()
    } else {
        smartRefreshLayout.finishLoadMore()
    }
    if (data.isSuccess) {
        //??????
        when {
            //???????????????????????? ?????????????????????
            data.isFirstEmpty -> {
                loadService.showEmpty()
            }
            //????????????
            data.isRefresh -> {
                baseQuickAdapter.setList(data.listData)
                loadService.showSuccess()
            }
            //???????????????
            else -> {
                baseQuickAdapter.addData(data.listData)
                loadService.showSuccess()
            }
        }
    } else {
        //??????
        if (data.isRefresh) {
            //??????????????????????????????????????????????????????????????????
            loadService.showError(data.errMessage)
        }
    }
}

/**
 * ???????????????
 */
fun LoadService<*>.showEmpty() {
    this.showCallback(EmptyCallback::class.java)
}


/**
 * ??????????????????
 * @param message ?????????????????????????????????
 */
fun LoadService<*>.showError(message: String = "") {
    this.setErrorText(message)
    this.showCallback(ErrorCallback::class.java)
}

/**
 * ?????????: hegaojian
 * ?????????: 2020/2/20
 * ?????????:????????????????????????????????????
 */


fun LoadService<*>.setErrorText(message: String) {
    if (message.isNotEmpty()) {
        this.setCallBack(ErrorCallback::class.java) { _, view ->
            view.findViewById<TextView>(R.id.error_text).text = message
        }
    }
}

/**
 * ????????????????????????toolbar
 */
fun Toolbar.initClose(
    titleStr: String = "",
    backImg: Int = R.drawable.ic_back,
    onBack: (toolbar: Toolbar) -> Unit
): Toolbar {
//    setBackgroundColor(SettingUtil.getColor(appContext))
    title = titleStr.toHtml()
    setNavigationIcon(backImg)
    setNavigationOnClickListener { onBack.invoke(this) }
    return this
}

/**
 * ???????????????
 */
fun hideSoftKeyboard(activity: Activity?) {
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

fun MagicIndicator.bindViewPager2(
    viewPager: ViewPager2,
    mStringList: MutableList<ProjectTree> = arrayListOf(),
    action: (index: Int) -> Unit = {}
) {
    val commonNavigator = CommonNavigator(appContext)
    commonNavigator.adapter = object : CommonNavigatorAdapter() {

        override fun getCount(): Int {
            return mStringList.size
        }

        override fun getTitleView(context: Context, index: Int): IPagerTitleView {
            return ClipPagerTitleView(context).apply {
                text = mStringList[index].name
                textColor = Color.WHITE
                clipColor = Color.WHITE

            }
//            return ScaleTransitionPagerTitleView(appContext).apply {
//                //????????????
//                text = mStringList[index].name.toHtml()
//                //????????????
//                textSize = 18F
//                //???????????????
//                normalColor = Color.WHITE
//                //????????????
//                selectedColor = Color.WHITE
//                //????????????
//                setOnClickListener {
//                    viewPager.currentItem = index
//                    action.invoke(index)
//                }
//            }
        }

        override fun getIndicator(context: Context): IPagerIndicator {
            return LinePagerIndicator(context).apply {
                mode = LinePagerIndicator.MODE_WRAP_CONTENT
                //??????????????????
//                lineHeight = ConvertUtils.dp2px(3F).toFloat()
//                lineWidth = ConvertUtils.dp2px(30F).toFloat()
//                //???????????????
//                roundRadius = ConvertUtils.dp2px(6F).toFloat()
//                startInterpolator = AccelerateInterpolator()
//                endInterpolator = DecelerateInterpolator(2.0f)
                //???????????????
                setColors(Color.WHITE)
            }
        }
    }
    this.navigator = commonNavigator

    viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            this@bindViewPager2.onPageSelected(position)
            action.invoke(position)
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            this@bindViewPager2.onPageScrolled(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            this@bindViewPager2.onPageScrollStateChanged(state)
        }
    })
}

fun ViewPager2.init(
    fragment: Fragment,
    fragments: MutableList<Fragment>,
    isUserInputEnabled: Boolean = true
): ViewPager2 {
    //???????????????
    this.isUserInputEnabled = isUserInputEnabled
    //???????????????
    adapter = object : FragmentStateAdapter(fragment) {
        override fun createFragment(position: Int) = fragments[position]
        override fun getItemCount() = fragments.size
    }
    return this
}

fun MagicIndicator.bindViewPager3(
    viewPager: ViewPager2,
    mStringList: MutableList<WxArticleTree> = arrayListOf(),
    action: (index: Int) -> Unit = {}
) {
    val commonNavigator = CommonNavigator(appContext)
    commonNavigator.adapter = object : CommonNavigatorAdapter() {

        override fun getCount(): Int {
            return mStringList.size
        }

        override fun getTitleView(context: Context, index: Int): IPagerTitleView {
            return ClipPagerTitleView(context).apply {
                text = mStringList[index].name
                textColor = Color.WHITE
                clipColor = Color.WHITE

            }
//            return ScaleTransitionPagerTitleView(appContext).apply {
//                //????????????
//                text = mStringList[index].name.toHtml()
//                //????????????
//                textSize = 18F
//                //???????????????
//                normalColor = Color.WHITE
//                //????????????
//                selectedColor = Color.WHITE
//                //????????????
//                setOnClickListener {
//                    viewPager.currentItem = index
//                    action.invoke(index)
//                }
//            }
        }

        override fun getIndicator(context: Context): IPagerIndicator {
            return LinePagerIndicator(context).apply {
                mode = LinePagerIndicator.MODE_WRAP_CONTENT
                //??????????????????
//                lineHeight = ConvertUtils.dp2px(3F).toFloat()
//                lineWidth = ConvertUtils.dp2px(30F).toFloat()
//                //???????????????
//                roundRadius = ConvertUtils.dp2px(6F).toFloat()
//                startInterpolator = AccelerateInterpolator()
//                endInterpolator = DecelerateInterpolator(2.0f)
                //???????????????
                setColors(Color.WHITE)
            }
        }
    }
    this.navigator = commonNavigator

    viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            this@bindViewPager3.onPageSelected(position)
            action.invoke(position)
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            this@bindViewPager3.onPageScrolled(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            this@bindViewPager3.onPageScrollStateChanged(state)
        }
    })
}

