package saloon.app.android.ui.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import saloon.app.android.R
import saloon.app.android.ui.base.AbstractViewHolder
import saloon.app.android.ui.base.QuestionViewHolder
import saloon.app.android.ui.base.RecyclerViewHolder
import saloon.app.android.ui.models.Model

private val FEED_LIST_ITEM_LIST = R.layout.item_recycler_view
private val FEED_LIST_ITEM_QUESTION = 1
private val FEED_LIST_ITEM_ARTICLE = 2

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
        } as AbstractViewHolder<Model>
    }

    override fun onBindViewHolder(holder: AbstractViewHolder<Model>, position: Int) =
        holder.bind(getItem(position)!!)
}