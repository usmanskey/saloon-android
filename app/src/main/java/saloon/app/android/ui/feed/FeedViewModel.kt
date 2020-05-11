package saloon.app.android.ui.feed

import androidx.lifecycle.ViewModel
import saloon.app.android.data.repository.QuestionsRepository

class FeedViewModel(private val questionsRepository: QuestionsRepository) : ViewModel() {

    val items = questionsRepository.getUserFeed()
}