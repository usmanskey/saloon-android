package saloon.app.android.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.firebase.ui.firestore.paging.LoadingState
import saloon.app.android.R
import saloon.app.android.data.models.Question
import saloon.app.android.ui.base.holders.AbstractViewHolder
import saloon.app.android.ui.base.holders.QuestionViewHolder

private const val FEED_LIST_ITEM_LIST = R.layout.item_recycler_view
private const val FEED_LIST_ITEM_QUESTION = R.layout.item_question
private const val FEED_LIST_ITEM_ARTICLE = 2

class FeedAdapter(
    config: FirestorePagingOptions<Question>,
    private val onRefreshingListener: (LoadingState) -> Unit
) :
    FirestorePagingAdapter<Question, AbstractViewHolder<Question>>(config) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AbstractViewHolder<Question> {
        val layout = LayoutInflater.from(parent.context).inflate(
            FEED_LIST_ITEM_QUESTION, parent, false
        )
        return QuestionViewHolder(
            layout
        )
    }


    override fun onBindViewHolder(
        holder: AbstractViewHolder<Question>,
        position: Int,
        model: Question
    ) {
        holder.bind(model)
    }

    override fun onLoadingStateChanged(state: LoadingState) {
        super.onLoadingStateChanged(state)
        onRefreshingListener(state)

    }
}