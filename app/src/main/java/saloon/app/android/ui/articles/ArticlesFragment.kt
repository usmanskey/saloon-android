package saloon.app.android.ui.articles

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.feed_fragment.*
import kotlinx.android.synthetic.main.feed_fragment.view.*
import kotlinx.android.synthetic.main.feed_fragment.view.feed_recycler
import saloon.app.android.R
import saloon.app.android.data.models.User
import saloon.app.android.data.repository.questions.QuestionsItemKeyed
import saloon.app.android.data.repository.questions.QuestionsRepositoryImpl
import saloon.app.android.ui.base.FeedAdapter
import saloon.app.android.ui.base.MarginItemDecoration
import saloon.app.android.ui.base.ModelsDiffUtilItemCallback
import saloon.app.android.ui.feed.FeedViewModel
import saloon.app.android.ui.feed.FeedViewModelProvider
import java.util.concurrent.Executors
import kotlin.math.roundToInt

class ArticlesFragment : Fragment(R.layout.feed_fragment) {

    private lateinit var viewModel: ArticlesViewModel

    private val diffUtilCallback by lazy(LazyThreadSafetyMode.NONE) {
        ModelsDiffUtilItemCallback()
    }

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        FeedAdapter(diffUtilCallback)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(
            this, ArticlesViewModelProvider(
                QuestionsRepositoryImpl(
                    QuestionsItemKeyed(
                        Firebase.firestore,
                        User("MujgUavCZIPuu5knemEp4FVF8hl2", "", "", "", 0, 0),
                        QuestionsRepositoryImpl.QUESTIONS_COLLECTION_NAME
                    ),
                    Executors.newSingleThreadExecutor(),
                    ContextCompat.getMainExecutor(context)
                )
            )
        ).get(ArticlesViewModel::class.java)
        adapter.submitList(viewModel.items)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.feed_recycler.adapter = adapter
        feed_recycler.addItemDecoration(
            MarginItemDecoration(
                resources.getDimension(R.dimen.feed_item_margin)
                    .roundToInt()
            )
        )
    }
}