package saloon.app.android.ui.base.holders

import android.view.View
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.item_question.view.*
import saloon.app.android.data.models.Question


class QuestionViewHolder(itemView: View) : AbstractViewHolder<Question>(itemView) {

    override fun bind(item: Question) {
        setImageUrl(item.imageUrl)
        setTitle(item.title)
        setDescription(item.author?.name)
    }

    private fun setTitle(title: String) {
        itemView.question_title.text = title
    }

    private fun setDescription(desc: String?) {
        itemView.question_description.text = desc
        itemView.question_description.isVisible = desc != null
    }

    private fun setImageUrl(imageUrl: String?) {
        val gsReference =
            FirebaseStorage.getInstance().getReferenceFromUrl("gs://saloon-1c4a1.appspot.com/")

        itemView.question_image.isVisible = if (imageUrl != null) {
            val imageReference = gsReference.child(imageUrl + "_1920x1080")
            imageReference.downloadUrl.addOnCompleteListener { task ->
                if (task.isSuccessful)
                    Glide.with(itemView.context).load(task.result).into(itemView.question_image)
            }
            true
        } else {
            false
        }
    }
}