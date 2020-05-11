package saloon.app.android.ui.articles

import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.ItemKeyedDataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import saloon.app.android.data.models.Author
import saloon.app.android.data.models.Model
import saloon.app.android.data.models.Question
import saloon.app.android.data.repository.QuestionsRepository

class ArticlesViewModel(private val questionsRepository: QuestionsRepository) : ViewModel() {

    val items = questionsRepository.getUserFeed()
}