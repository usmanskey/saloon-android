package saloon.app.android.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import saloon.app.android.R
import saloon.app.android.data.models.Model
import saloon.app.android.data.models.Question
import saloon.app.android.ui.base.holders.AbstractViewHolder
import saloon.app.android.ui.base.holders.QuestionViewHolder
import saloon.app.android.ui.base.holders.RecyclerViewHolder

private const val FEED_LIST_ITEM_LIST = R.layout.item_recycler_view
private const val FEED_LIST_ITEM_QUESTION = R.layout.item_question
private const val FEED_LIST_ITEM_ARTICLE = 2

class FeedAdapter(private val diffCallback: DiffUtil.ItemCallback<Model>) :
    PagedListAdapter<Model, AbstractViewHolder<Model>>(diffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AbstractViewHolder<Model> {
        val layout = LayoutInflater.from(parent.context).inflate(
            viewType, parent, false
        )
        return when (viewType) {
            FEED_LIST_ITEM_LIST -> RecyclerViewHolder(
                layout,
                R.id.recycler_view,
                FeedAdapter(diffCallback)
            )
            FEED_LIST_ITEM_QUESTION -> QuestionViewHolder(
                layout
            )
            else -> throw IllegalArgumentException(viewType.toString())
        }
    }

    override fun getItemViewType(position: Int) = when (getItem(position)?.item) {
        is List<*> -> FEED_LIST_ITEM_LIST
        is Question -> FEED_LIST_ITEM_QUESTION
        else -> FEED_LIST_ITEM_ARTICLE
    }

    override fun onBindViewHolder(holder: AbstractViewHolder<Model>, position: Int) =
        holder.bind(getItem(position)!!)

    override fun onCurrentListChanged(
        previousList: PagedList<Model>?,
        currentList: PagedList<Model>?
    ) {
        super.onCurrentListChanged(previousList, currentList)
    }
}