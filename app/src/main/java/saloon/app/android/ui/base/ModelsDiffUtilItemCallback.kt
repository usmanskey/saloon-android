package saloon.app.android.ui.base

import androidx.recyclerview.widget.DiffUtil
import saloon.app.android.data.models.Model

class ModelsDiffUtilItemCallback : DiffUtil.ItemCallback<Model>() {
    override fun areItemsTheSame(oldItem: Model, newItem: Model): Boolean =
        oldItem.item == newItem.item

    override fun areContentsTheSame(oldItem: Model, newItem: Model): Boolean = false
}