package saloon.app.android.ui.base

import androidx.recyclerview.widget.DiffUtil
import saloon.app.android.data.models.Model
import saloon.app.android.data.models.Question


class QuestionDiffUtilItemCallback : DiffUtil.ItemCallback<Model>() {
    override fun areItemsTheSame(oldItem: Model, newItem: Model): Boolean {
        val oldQuestion = oldItem.item as Question
        val newQuestion = newItem.item as Question

        return oldQuestion.id == newQuestion.id
    }

    override fun areContentsTheSame(oldItem: Model, newItem: Model): Boolean {
        val oldQuestion = oldItem.item as Question
        val newQuestion = newItem.item as Question

        return oldQuestion.title == newQuestion.title
                && oldQuestion.imageUrl == newQuestion.imageUrl
    }

    override fun getChangePayload(oldItem: Model, newItem: Model): Any? {
        val oldQuestion = oldItem.item as Question
        val newQuestion = newItem.item as Question

        return when {
            oldQuestion.title != newQuestion.title
                    && oldQuestion.imageUrl != newQuestion.imageUrl
                    && oldQuestion.author?.name != newQuestion.author?.name ->
                intArrayOf(
                    QUESTION_PAYLOAD_TITLE,
                    QUESTION_PAYLOAD_IMAGE_URL,
                    QUESTION_PAYLOAD_AUTHOR
                )

            oldQuestion.title != newQuestion.title -> QUESTION_PAYLOAD_TITLE
            oldQuestion.imageUrl != newQuestion.imageUrl -> QUESTION_PAYLOAD_IMAGE_URL
            oldQuestion.author?.name != newQuestion.author?.name -> QUESTION_PAYLOAD_AUTHOR
            else -> -1
        }

    }

    companion object {

        const val QUESTION_PAYLOAD_TITLE = 0
        const val QUESTION_PAYLOAD_IMAGE_URL = 1
        const val QUESTION_PAYLOAD_AUTHOR = 2

    }
}