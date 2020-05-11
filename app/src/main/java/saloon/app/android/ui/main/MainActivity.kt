package saloon.app.android.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.coroutines.MainScope
import saloon.app.android.R
import saloon.app.android.ui.articles.ArticlesFragment
import saloon.app.android.ui.feed.FeedFragment
import saloon.app.android.ui.main.MainAdapter
import saloon.app.android.ui.notifications.NotificationsFragment
import saloon.app.android.ui.questions.create.QuestionCreateFragment

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
    }
}