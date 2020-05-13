package saloon.app.android.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.AppBarLayout.ScrollingViewBehavior
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.main_activity.*
import saloon.app.android.R
import saloon.app.android.ui.articles.ArticlesFragment
import saloon.app.android.ui.feed.FeedFragment
import saloon.app.android.ui.notifications.NotificationsFragment
import saloon.app.android.ui.questions.creator.QuestionCreateFragment


class MainActivity : AppCompatActivity(R.layout.main_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val adapter = MainAdapter(
            this, arrayOf(
                QuestionCreateFragment(),
                FeedFragment(),
                ArticlesFragment(),
                NotificationsFragment()
            )
        )
        main_view_pager.adapter = adapter

        main_view_pager.setCurrentItem(1, true)
        TabLayoutMediator(main_tabs, main_view_pager) { tab, position ->
            when (position) {
                0 -> tab.icon = getDrawable(R.drawable.ic_outline_create_24)
                1 -> tab.text = getString(R.string.feed_title)
                2 -> tab.text = getString(R.string.articles_title)
                3 -> tab.icon = getDrawable(R.drawable.ic_baseline_notifications_24)
            }
        }.attach()

        main_view_pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                val lParams = main_view_pager.layoutParams as CoordinatorLayout.LayoutParams
                if (position == 0 && lParams.behavior != null) {
                    appbar.animate().translationY(-appbar.height.toFloat())
                    lParams.behavior = null
                    main_view_pager.requestLayout()
                } else if (lParams.behavior == null) {
                    appbar.animate().translationY(0F)
                    (main_view_pager.layoutParams as CoordinatorLayout.LayoutParams).behavior =
                        ScrollingViewBehavior()
                    main_view_pager.requestLayout()
                }
            }
        })
    }

    fun scrollToFeed() {
        main_view_pager.setCurrentItem(1, true)
    }
}