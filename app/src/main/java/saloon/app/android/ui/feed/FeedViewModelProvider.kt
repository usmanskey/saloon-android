package saloon.app.android.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import saloon.app.android.data.repository.QuestionsRepository
import saloon.app.android.data.repository.UsersRepository

class FeedViewModelProvider(
    private val usersRepository: UsersRepository,
    private val questionsRepository: QuestionsRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        FeedViewModel(usersRepository, questionsRepository) as T
}