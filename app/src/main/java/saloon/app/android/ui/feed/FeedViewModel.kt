package saloon.app.android.ui.feed

import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import saloon.app.android.data.models.Model
import saloon.app.android.data.repository.QuestionsRepository
import saloon.app.android.data.repository.UsersRepository

class FeedViewModel(
    private val usersRepository: UsersRepository,
    private val questionsRepository: QuestionsRepository
) : ViewModel() {

    val items: PagedList<Model>
        get() = questionsRepository.getUserFeed()

    suspend fun getUser() = usersRepository.getUser()

    fun invalidateDataSource() = questionsRepository.invalidateFeed()
}