package saloon.app.android.data.repository.questions

import androidx.paging.PagedList
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firestore.v1.FirestoreGrpc
import kotlinx.coroutines.tasks.await
import saloon.app.android.data.models.Answer
import saloon.app.android.data.models.Model
import saloon.app.android.data.models.Question
import saloon.app.android.data.models.User
import saloon.app.android.data.repository.QuestionsRepository
import saloon.app.android.data.repository.user.UsersRepositoryImpl
import java.lang.Exception
import java.util.concurrent.Executor

class QuestionsRepositoryImpl(
    private val questionsItemKeyed: QuestionsItemKeyed,
    private val fetchExecutor: Executor,
    private val notifyExecutor: Executor
) : QuestionsRepository {

    override fun getUserFeed(): PagedList<Model> = PagedList.Builder(
        questionsItemKeyed, 15
    )
        .setFetchExecutor(fetchExecutor)
        .setNotifyExecutor(notifyExecutor)
        .build()

    override fun invalidateFeed() = questionsItemKeyed.invalidate()

    override suspend fun createQuestion(userId: String, question: Question): Boolean {
        val doc = Firebase.firestore.collection(UsersRepositoryImpl.USERS_COLLECTION_NAME)
            .document(userId)
            .collection(QUESTIONS_COLLECTION_NAME)
            .document()

        return try {
            doc.set(question.apply { id = doc.id }).await()
            true
        } catch (e: Exception) {
            false
        }
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