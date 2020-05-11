package saloon.app.android.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import saloon.app.android.data.repository.QuestionsRepository

class FeedViewModelProvider(private val questionsRepository: QuestionsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        FeedViewModel(questionsRepository) as T
}