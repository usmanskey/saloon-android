package saloon.app.android.ui.questions.creator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import saloon.app.android.data.repository.QuestionsRepository

class QuestionCreatorViewModelProvider(private val questionsRepository: QuestionsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return QuestionCreatorViewModel(questionsRepository) as T
    }
}