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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.blankj.utilcode.util.ConvertUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.app.appContext
import com.hp.jetpack.demo.network.state.ListDataUiState
import com.hp.jetpack.demo.weight.loadCallBack.EmptyCallback
import com.hp.jetpack.demo.weight.loadCallBack.ErrorCallback
import com.hp.jetpack.demo.weight.loadCallBack.LoadingCallback
import com.hp.jetpack.demo.weight.recyclerview.DefineLoadMoreView
import com.hp.jetpack.demo.weight.viewpage.ScaleTransitionPagerTitleView
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator

fun loadServiceInit(view: View, callback: () -> Unit): LoadService<Any> {
    val loadSir = LoadSir.getDefault().register(view) {
        //点击重试时触发的操作
        callback.invoke()
    }
    loadSir.showSuccess()
//    SettingUtil.setLoadingColor(SettingUtil.getColor(appContext), loadsir)
    return loadSir
}

/**
 * 设置加载中
 */
fun LoadService<*>.showLoading() {
    this.showCallback(LoadingCallback::class.java)
}

//绑定SwipeRecyclerView
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
    //给尾部设置颜色
//    footerView.setLoadViewColor(SettingUtil.getOneColorStateList(appContext))
    //设置尾部点击回调
    footerView.setmLoadMoreListener(SwipeRecyclerView.LoadMoreListener {
        footerView.onLoading()
        loadMoreListener.onLoadMore()
    })
    this.run {
        //添加加载更多尾部
        addFooterView(footerView)
        setLoadMoreView(footerView)
        //设置加载更多回调
        setLoadMoreListener(loadMoreListener)
    }
    return footerView
}

fun RecyclerView.initFloatBtn(floatBtn: FloatingActionButton) {
    //监听recyclerview滑动到顶部的时候，需要把向上返回顶部的按钮隐藏
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
        //如果当前recyclerview 最后一个视图位置的索引大于等于40，则迅速返回顶部，否则带有滚动动画效果返回到顶部
        if (layoutManager.findLastVisibleItemPosition() >= 40) {
            scrollToPosition(0)//没有动画迅速返回到顶部(马上)
        } else {
            smoothScrollToPosition(0)//有滚动动画返回到顶部(有点慢)
        }
    }
}

/**
 * 加载列表数据
 */
fun <T> loadListData(
    data: ListDataUiState<T>,
    baseQuickAdapter: BaseQuickAdapter<T, *>,
    loadService: LoadService<*>,
    recyclerView: SwipeRecyclerView,
    swipeRefreshLayout: SwipeRefreshLayout
) {
    swipeRefreshLayout.isRefreshing = false
    recyclerView.loadMoreFinish(data.isEmpty, data.hasMore)
    if (data.isSuccess) {
        //成功
        when {
            //第一页并没有数据 显示空布局界面
            data.isFirstEmpty -> {
                loadService.showEmpty()
            }
            //是第一页
            data.isRefresh -> {
                baseQuickAdapter.setList(data.listData)
                loadService.showSuccess()
            }
            //不是第一页
            else -> {
                baseQuickAdapter.addData(data.listData)
                loadService.showSuccess()
            }
        }
    } else {
        //失败
        if (data.isRefresh) {
            //如果是第一页，则显示错误界面，并提示错误信息
            loadService.showError(data.errMessage)
        } else {
            recyclerView.loadMoreError(0, data.errMessage)
        }
    }
}

/**
 * 设置空布局
 */
fun LoadService<*>.showEmpty() {
    this.showCallback(EmptyCallback::class.java)
}


/**
 * 设置错误布局
 * @param message 错误布局显示的提示内容
 */
fun LoadService<*>.showError(message: String = "") {
    this.setErrorText(message)
    this.showCallback(ErrorCallback::class.java)
}

/**
 * 作者　: hegaojian
 * 时间　: 2020/2/20
 * 描述　:项目中自定义类的拓展函数
 */


fun LoadService<*>.setErrorText(message: String) {
    if (message.isNotEmpty()) {
        this.setCallBack(ErrorCallback::class.java) { _, view ->
            view.findViewById<TextView>(R.id.error_text).text = message
        }
    }
}

/**
 * 初始化有返回键的toolbar
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
 * 隐藏软键盘
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
    mStringList: MutableList<String> = arrayListOf(),
    action: (index: Int) -> Unit = {}) {
    val commonNavigator = CommonNavigator(appContext)
    commonNavigator.adapter = object : CommonNavigatorAdapter() {

        override fun getCount(): Int {
            return  mStringList.size
        }
        override fun getTitleView(context: Context, index: Int): IPagerTitleView {
            return ScaleTransitionPagerTitleView(appContext).apply {
                //设置文本
                text = mStringList[index].toHtml()
                //字体大小
                textSize = 22F
                //未选中颜色
                normalColor = Color.WHITE
                //选中颜色
                selectedColor = Color.WHITE
                //点击事件
                setOnClickListener {
                    viewPager.currentItem = index
                    action.invoke(index)
                }
            }
        }
        override fun getIndicator(context: Context): IPagerIndicator {
            return LinePagerIndicator(context).apply {
                mode = LinePagerIndicator.MODE_EXACTLY
                //线条的宽高度
                lineHeight = ConvertUtils.dp2px(3F).toFloat()
                lineWidth = ConvertUtils.dp2px(30F).toFloat()
                //线条的圆角
                roundRadius = ConvertUtils.dp2px(6F).toFloat()
                startInterpolator = AccelerateInterpolator()
                endInterpolator = DecelerateInterpolator(2.0f)
                //线条的颜色
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
    //是否可滑动
    this.isUserInputEnabled = isUserInputEnabled
    //设置适配器
    adapter = object : FragmentStateAdapter(fragment) {
        override fun createFragment(position: Int) = fragments[position]
        override fun getItemCount() = fragments.size
    }
    return this
}


