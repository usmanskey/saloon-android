package saloon.app.android.ui.questions.info

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import saloon.app.android.data.models.Question
import saloon.app.android.data.repository.QuestionsRepository
import saloon.app.android.data.repository.questions.QuestionsItemKeyedFactory
import saloon.app.android.data.repository.questions.QuestionsRepositoryImpl

class QuestionViewModel(private val questionsRepository: QuestionsRepository = QuestionsRepositoryImpl(QuestionsItemKeyedFactory(Firebase.firestore, Firebase.auth.uid!!, QuestionsRepositoryImpl.QUESTIONS_COLLECTION_NAME))) : ViewModel() {

    suspend fun getQuestion(id: String): Question? = questionsRepository.getQuestion(id)
}