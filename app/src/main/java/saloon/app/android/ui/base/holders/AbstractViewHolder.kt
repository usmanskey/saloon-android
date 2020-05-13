package saloon.app.android.ui.base.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class AbstractViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bind(item: T)

    open fun bind(item: T, payloads: MutableList<Any>) =
        bind(item)
}