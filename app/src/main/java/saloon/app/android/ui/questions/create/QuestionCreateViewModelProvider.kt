package saloon.app.android.ui.questions.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import saloon.app.android.data.repository.QuestionsRepository

class QuestionCreateViewModelProvider(private val questionsRepository: QuestionsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return QuestionCreateViewModel(questionsRepository) as T
    }
}