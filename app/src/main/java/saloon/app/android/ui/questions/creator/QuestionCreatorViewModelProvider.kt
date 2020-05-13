package saloon.app.android.ui.questions.creator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import saloon.app.android.data.repository.QuestionsRepository
import saloon.app.android.data.repository.UsersRepository

@Suppress("UNCHECKED_CAST")
class QuestionCreatorViewModelProvider(
    private val questionsRepository: QuestionsRepository,
    private val usersRepository: UsersRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        QuestionCreatorViewModel(questionsRepository, usersRepository) as T
}