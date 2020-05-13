package saloon.app.android.ui.feed

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.feed_fragment.*
import saloon.app.android.R
import saloon.app.android.data.repository.questions.QuestionsItemKeyedFactory
import saloon.app.android.data.repository.questions.QuestionsRepositoryImpl
import saloon.app.android.data.repository.user.UsersRepositoryImpl
import saloon.app.android.ui.base.FeedAdapter
import saloon.app.android.ui.base.MarginItemDecoration
import saloon.app.android.ui.base.ModelsDiffUtilItemCallback
import kotlin.math.roundToInt

class FeedFragment : Fragment(R.layout.feed_fragment) {

    private lateinit var viewModel: FeedViewModel

    private val diffUtilCallback by lazy(LazyThreadSafetyMode.NONE) {
        ModelsDiffUtilItemCallback()
    }

    private var allowRefresh = false

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        FeedAdapter(diffUtilCallback)
    }

    init {
        lifecycleScope.launchWhenCreated {
            viewModel = ViewModelProvider(
                this@FeedFragment, FeedViewModelProvider(
                    UsersRepositoryImpl(Firebase.firestore, Firebase.auth),
                    QuestionsRepositoryImpl(
                        QuestionsItemKeyedFactory(
                            Firebase.firestore,
                            Firebase.auth.uid!!,
                            QuestionsRepositoryImpl.QUESTIONS_COLLECTION_NAME
                        )
                    )
                )
            ).get(FeedViewModel::class.java)

            viewModel.items.observe(this@FeedFragment, Observer {
                adapter.submitList(it)
                feed_swipe_refresh.isRefreshing = false
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        feed_swipe_refresh.setOnRefreshListener {
            viewModel.invalidateDataSource()
        }
        feed_recycler.adapter = adapter
        feed_recycler.addItemDecoration(
            MarginItemDecoration(
                resources.getDimension(R.dimen.feed_item_margin)
                    .roundToInt(), 1
            )
        )
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (allowRefresh && isVisibleToUser) viewModel.invalidateDataSource()
        allowRefresh = !isVisibleToUser
    }

    override fun onPause() {
        super.onPause()
        allowRefresh = true
    }
}