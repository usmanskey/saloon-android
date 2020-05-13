package saloon.app.android.data.repository.questions

import androidx.paging.DataSource
import com.google.firebase.firestore.FirebaseFirestore
import saloon.app.android.data.models.Model

class QuestionsItemKeyedFactory(private val db: FirebaseFirestore, private val userId: String, private val questionsCollectionName: String): DataSource.Factory<Long, Model>() {
    override fun create(): DataSource<Long, Model> =
        QuestionsItemKeyed(db, userId, questionsCollectionName)
}