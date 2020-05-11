package saloon.app.android.ui.articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import saloon.app.android.data.repository.QuestionsRepository

class ArticlesViewModelProvider(private val questionsRepository: QuestionsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        ArticlesViewModel(questionsRepository) as T
}