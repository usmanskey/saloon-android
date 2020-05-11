package saloon.app.android.data.repository

import androidx.paging.PagedList
import saloon.app.android.data.models.Answer
import saloon.app.android.data.models.Model
import saloon.app.android.data.models.Question
import saloon.app.android.data.models.User

interface QuestionsRepository {

    fun getUserFeed(): PagedList<Model>

    suspend fun createQuestion(question: Question): Boolean

    suspend fun getQuestion(id: String): Question

    suspend fun addAnswer(answer: Answer, id: String): Boolean
}