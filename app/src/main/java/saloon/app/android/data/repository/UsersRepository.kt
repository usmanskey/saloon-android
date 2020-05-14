package saloon.app.android.data.repository

import saloon.app.android.data.models.QuestionId
import saloon.app.android.data.models.User

interface UsersRepository {

    suspend fun getUser(id: String): User?

    suspend fun getUser(): User?

    suspend fun saveUser(user: User)

    suspend fun addQuestionToUser(questionId: QuestionId)
}