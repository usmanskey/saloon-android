package saloon.app.android.ui.base.holders

import android.view.View
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.item_question.view.*
import saloon.app.android.data.models.Model
import saloon.app.android.data.models.Question


class QuestionViewHolder(itemView: View) : AbstractViewHolder<Model>(itemView) {

    override fun bind(item: Model) {
        val question = item.item as Question
        val gsReference =
            FirebaseStorage.getInstance().getReferenceFromUrl("gs://saloon-1c4a1.appspot.com")

        val imageReference = gsReference.child(question.imageUrl)
        imageReference.downloadUrl.addOnCompleteListener {
            if (it.isSuccessful)
                Glide.with(itemView.context).load(it.result).into(itemView.question_image)

        }
        itemView.question_title.text = question.title
        itemView.question_description.text = question.author?.name
    }
}