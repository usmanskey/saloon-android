package saloon.app.android.data.repository.questions

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import saloon.app.android.data.models.Answer
import saloon.app.android.data.models.Model
import saloon.app.android.data.models.Question
import saloon.app.android.data.repository.QuestionsRepository
import saloon.app.android.data.repository.user.UsersRepositoryImpl
import java.util.*

class QuestionsRepositoryImpl(
    questionsItemKeyedFactory: DataSource.Factory<Long, Model>
) : QuestionsRepository {

    private val feedLiveData = questionsItemKeyedFactory.toLiveData(
        PagedList.Config.Builder()
            .setInitialLoadSizeHint(20)
            .setPrefetchDistance(40)
            .build(),
        Date().time
    )

    override fun getUserFeed(): LiveData<PagedList<Model>> = feedLiveData

    override fun invalidateFeed() = feedLiveData.value?.dataSource?.invalidate() ?: Unit

    override suspend fun createQuestion(userId: String, question: Question) = try {
        val db = Firebase.firestore

        val doc = db.collection(UsersRepositoryImpl.USERS_COLLECTION_NAME)
            .document(userId)
            .collection(QUESTIONS_COLLECTION_NAME)
            .document()
        doc.set(question.apply { id = doc.id }).await()
        true
    } catch (e: Exception) {
        false
    }

    override suspend fun getQuestion(id: String): Question {
        TODO("Not yet implemented")
    }

    override suspend fun addAnswer(answer: Answer, id: String): Boolean {
        TODO("Not yet implemented")
    }

    companion object {
        const val QUESTIONS_COLLECTION_NAME = "questions"
        const val RECOMMENDATIONS_COLLECTION_NAME = "recommendations"
    }
}