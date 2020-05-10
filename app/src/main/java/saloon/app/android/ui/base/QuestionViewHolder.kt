package saloon.app.android.ui.base

import android.view.View
import kotlinx.android.synthetic.main.item_question.view.*
import saloon.app.android.data.models.Question
import saloon.app.android.ui.models.Model

class QuestionViewHolder(itemView: View) : AbstractViewHolder<Model>(itemView) {

    override fun bind(item: Model) {
        val question = item.item as Question
        itemView.question_title.text = question.title
        itemView.question_description.text = question.author.name
    }
}