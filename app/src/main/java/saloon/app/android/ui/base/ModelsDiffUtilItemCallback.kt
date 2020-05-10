package saloon.app.android.ui.base

import androidx.recyclerview.widget.DiffUtil
import saloon.app.android.ui.models.Model

class ModelsDiffUtilItemCallback : DiffUtil.ItemCallback<Model>() {
    override fun areItemsTheSame(oldItem: Model, newItem: Model): Boolean {
        return oldItem.item == newItem.item
    }

    override fun getChangePayload(oldItem: Model, newItem: Model): Any? {
        return super.getChangePayload(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: Model, newItem: Model): Boolean {
        return true
    }
}