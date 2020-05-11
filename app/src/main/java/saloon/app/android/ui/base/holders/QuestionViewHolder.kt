package saloon.app.android.ui.base.holders

import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_question.view.*
import saloon.app.android.data.models.Question
import saloon.app.android.data.models.Model

class QuestionViewHolder(itemView: View) : AbstractViewHolder<Model>(itemView) {

    override fun bind(item: Model) {
        val question = item.item as Question

        Glide.with(itemView).load(question.imageUrl).into(itemView.question_image)
        itemView.question_title.text = question.title
        itemView.question_description.text = question.author.name
    }
}