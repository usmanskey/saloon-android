package saloon.app.android.ui.feed

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.ItemKeyedDataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import saloon.app.android.data.models.Author
import saloon.app.android.data.models.Question
import saloon.app.android.ui.models.Model

class FeedViewModel : ViewModel() {

    private val items = listOf(Model().apply {
        setT(
            Question(
                "asd3",
                "Is there anyone?",
                Author("asd", "Usman Akhmedov"),
                emptyList(),
                ""
            )
        )
    }, Model().apply {
        setT(
            Question(
                "asd2",
                "Is there anyone?",
                Author("asd", "Usman Akhmedov"),
                emptyList(),
                ""
            )
        )
    })

    val pagedList = LivePagedListBuilder<String, Model>(
        object : DataSource.Factory<String?, Model?>() {
            override fun create(): DataSource<String?, Model?> {
                return object : ItemKeyedDataSource<String, Model>() {
                    override fun loadInitial(
                        params: LoadInitialParams<String>,
                        callback: LoadInitialCallback<Model>
                    ) {
                        Log.d("ad", "res")
                        callback.onResult(items)
                    }

                    override fun loadAfter(
                        params: LoadParams<String>,
                        callback: LoadCallback<Model>
                    ) {
                        TODO("Not yet implemented")
                    }

                    override fun loadBefore(
                        params: LoadParams<String>,
                        callback: LoadCallback<Model>
                    ) {
                        TODO("Not yet implemented")
                    }

                    override fun getKey(item: Model): String = item.item.toString()
                }
            }
        }, PagedList.Config.Builder().build()
    ).build()
}