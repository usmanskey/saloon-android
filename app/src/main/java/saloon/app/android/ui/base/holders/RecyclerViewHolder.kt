package saloon.app.android.ui.base.holders

import android.view.View
import androidx.annotation.IdRes
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import saloon.app.android.ui.models.Model


/**
 * Universal
 * {@link ViewHolder}
 * for lists inside
 * {@link RecyclerView}
 *
 * @author Usman Akhmedov
 *
 * @since 1.0
 * */
class RecyclerViewHolder<T : Model>(
    itemView: View,
    @IdRes private val recyclerId: Int,
    private val adapter: PagedListAdapter<Model, AbstractViewHolder<Model>>,
    decorators: List<RecyclerView.ItemDecoration>? = null
) : AbstractViewHolder<T>(itemView) {

    init {
        val recycler = itemView.findViewById<RecyclerView>(recyclerId)

        recycler.adapter = adapter

        decorators?.forEach {
            recycler.addItemDecoration(it)
        }
    }

    override fun bind(item: T) = adapter.submitList(item.item as PagedList<Model>)

}