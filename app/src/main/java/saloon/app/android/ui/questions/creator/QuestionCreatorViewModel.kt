package saloon.app.android.ui.questions.creator

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import saloon.app.android.data.models.Question
import saloon.app.android.data.models.User
import saloon.app.android.data.repository.QuestionsRepository
import saloon.app.android.data.repository.UsersRepository

class QuestionCreatorViewModel(
    private val questionsRepository: QuestionsRepository,
    private val usersRepository: UsersRepository
) : ViewModel() {

    suspend fun createQuestion(question: Question) =
        questionsRepository.createQuestion(Firebase.auth.uid!!, question)

    suspend fun getUser() = usersRepository.getUser()
}
