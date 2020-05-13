package saloon.app.android.ui.articles

import androidx.lifecycle.ViewModel
import saloon.app.android.data.repository.QuestionsRepository

class ArticlesViewModel(private val questionsRepository: QuestionsRepository) : ViewModel() {

    val items = questionsRepository.getUserFeed()
}