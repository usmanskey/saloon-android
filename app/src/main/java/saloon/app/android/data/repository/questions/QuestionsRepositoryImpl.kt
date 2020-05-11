package saloon.app.android.data.repository.questions

import androidx.paging.PagedList
import saloon.app.android.data.models.Answer
import saloon.app.android.data.models.Model
import saloon.app.android.data.models.Question
import saloon.app.android.data.models.User
import saloon.app.android.data.repository.QuestionsRepository
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

    override suspend fun createQuestion(question: Question): Boolean {
        TODO("Not yet implemented")
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