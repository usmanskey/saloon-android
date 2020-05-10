package saloon.app.android.ui.base

import android.view.View
import androidx.annotation.IdRes
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView


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
class RecyclerViewHolder<T>(
    itemView: View,
    @IdRes private val recyclerId: Int,
    private val adapter: PagedListAdapter<T, AbstractViewHolder<T>>,
    decorators: List<RecyclerView.ItemDecoration>? = null
) : AbstractViewHolder<PagedList<T>>(itemView) {

    init {
        val recycler = itemView.findViewById<RecyclerView>(recyclerId)

        recycler.adapter = adapter

        decorators?.forEach {
            recycler.addItemDecoration(it)
        }
    }

    override fun bind(item: PagedList<T>) = adapter.submitList(item)

}