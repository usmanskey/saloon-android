package saloon.app.android.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.coroutines.MainScope
import saloon.app.android.R
import saloon.app.android.ui.feed.FeedFragment
import saloon.app.android.ui.main.MainAdapter

class MainActivity : AppCompatActivity(R.layout.main_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val adapter = MainAdapter(this, arrayOf(FeedFragment()))

        main_view_pager.adapter = adapter
    }
}