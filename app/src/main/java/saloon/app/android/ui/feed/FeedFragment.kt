package saloon.app.android.ui.feed

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagedList
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.firebase.ui.firestore.paging.LoadingState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.feed_fragment.*
import saloon.app.android.R
import saloon.app.android.data.models.Question
import saloon.app.android.data.repository.questions.QuestionsItemKeyedFactory
import saloon.app.android.data.repository.questions.QuestionsRepositoryImpl
import saloon.app.android.data.repository.user.UsersRepositoryImpl
import saloon.app.android.ui.base.FeedAdapter
import saloon.app.android.ui.base.MarginItemDecoration
import kotlin.math.roundToInt

class FeedFragment : Fragment(R.layout.feed_fragment) {

    private lateinit var viewModel: FeedViewModel
//
//    private val diffUtilCallback by lazy(LazyThreadSafetyMode.NONE) {
//        QuestionDiffUtilItemCallback()
//    }

    private var allowRefresh = false

    private val db by lazy(LazyThreadSafetyMode.NONE) {
        Firebase.firestore
    }

    private val query by lazy(LazyThreadSafetyMode.NONE) {
        db.collection(UsersRepositoryImpl.USERS_COLLECTION_NAME)
            .document(Firebase.auth.uid!!)
            .collection(QuestionsRepositoryImpl.QUESTIONS_COLLECTION_NAME)
            .orderBy("date", Query.Direction.DESCENDING)
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
                    query,
                    config,
                    Question::class.java
                )
                .build()
        ) {
            feed_swipe_refresh.isRefreshing = it == LoadingState.LOADING_INITIAL
                    || LoadingState.LOADING_MORE == it
        }
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
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        feed_swipe_refresh.setOnRefreshListener {
            adapter.refresh()
        }

        query.addSnapshotListener { _, _ ->
            adapter.refresh()
        }

        feed_recycler.adapter = adapter
        feed_recycler.addItemDecoration(
            MarginItemDecoration(
                resources.getDimension(R.dimen.feed_item_margin).roundToInt(), 1
            )
        )
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
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