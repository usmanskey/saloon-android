package saloon.app.android.ui.feed

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.feed_fragment.view.*
import saloon.app.android.R
import saloon.app.android.ui.base.ModelsDiffUtilItemCallback

class FeedFragment : Fragment(R.layout.feed_fragment) {

    private lateinit var viewModel: FeedViewModel

    private val diffUtilCallback by lazy(LazyThreadSafetyMode.NONE) {
        ModelsDiffUtilItemCallback()
    }

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        FeedAdapter(diffUtilCallback)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FeedViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.feed_recycler.adapter = adapter
    }
}