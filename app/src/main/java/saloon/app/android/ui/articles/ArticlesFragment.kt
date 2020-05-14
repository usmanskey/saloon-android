package saloon.app.android.ui.articles

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.firebase.ui.firestore.paging.LoadingState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.feed_fragment.*
import kotlinx.android.synthetic.main.feed_fragment.view.*
import saloon.app.android.R
import saloon.app.android.data.models.Question
import saloon.app.android.data.repository.questions.QuestionsItemKeyedFactory
import saloon.app.android.data.repository.questions.QuestionsRepositoryImpl
import saloon.app.android.data.repository.user.UsersRepositoryImpl
import saloon.app.android.ui.base.FeedAdapter
import saloon.app.android.ui.base.MarginItemDecoration
import kotlin.math.roundToInt

class ArticlesFragment : Fragment(R.layout.feed_fragment) {

    private lateinit var viewModel: ArticlesViewModel

    private val db by lazy(LazyThreadSafetyMode.NONE) {
        Firebase.firestore
    }

    private val config = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPrefetchDistance(2)
        .setPageSize(10)
        .build()

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        FeedAdapter(
            FirestorePagingOptions.Builder<Question>()
                .setLifecycleOwner(this)
                .setQuery(
                    db.collection(UsersRepositoryImpl.USERS_COLLECTION_NAME)
                        .document(Firebase.auth.uid!!)
                        .collection(QuestionsRepositoryImpl.QUESTIONS_COLLECTION_NAME)
                        .orderBy("date", Query.Direction.DESCENDING),
                    config,
                    Question::class.java
                )
                .build()
        ) {
            feed_swipe_refresh.isRefreshing = it == LoadingState.LOADING_INITIAL
                    || LoadingState.LOADING_MORE == it
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(
            this, ArticlesViewModelProvider(
                QuestionsRepositoryImpl(
                    QuestionsItemKeyedFactory(
                        Firebase.firestore,
                        Firebase.auth.uid!!,
                        QuestionsRepositoryImpl.QUESTIONS_COLLECTION_NAME
                    )
                )
            )
        ).get(ArticlesViewModel::class.java)
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