package saloon.app.android.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import saloon.app.android.data.models.Answer
import saloon.app.android.data.models.Model
import saloon.app.android.data.models.Question

interface QuestionsRepository {

    fun getUserFeed(): LiveData<PagedList<Model>>

    fun invalidateFeed()

    suspend fun createQuestion(userId: String, question: Question): Boolean

    suspend fun getQuestion(id: String): Question

    suspend fun addAnswer(answer: Answer, id: String): Boolean
}