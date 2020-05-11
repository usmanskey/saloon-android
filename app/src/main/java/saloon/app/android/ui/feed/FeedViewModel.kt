package saloon.app.android.ui.feed

import androidx.lifecycle.ViewModel
import saloon.app.android.data.repository.QuestionsRepository
import saloon.app.android.data.repository.UsersRepository

class FeedViewModel(
    private val usersRepository: UsersRepository,
    private val questionsRepository: QuestionsRepository
) : ViewModel() {

    suspend fun getUser() = usersRepository.getUser()

    val items = questionsRepository.getUserFeed()
}