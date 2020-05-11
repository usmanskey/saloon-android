package saloon.app.android.ui.questions.create

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import saloon.app.android.data.models.Question
import saloon.app.android.data.repository.QuestionsRepository

class QuestionCreateViewModel(private val questionsRepository: QuestionsRepository) : ViewModel() {

    suspend fun createQuestion(question: Question) {
        questionsRepository.createQuestion(Firebase.auth.uid!!, question)
    }
}