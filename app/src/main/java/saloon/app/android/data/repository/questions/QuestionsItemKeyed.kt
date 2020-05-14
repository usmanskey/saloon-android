package saloon.app.android.data.repository.questions

import android.util.Log
import androidx.paging.ItemKeyedDataSource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import saloon.app.android.data.models.Model
import saloon.app.android.data.models.Question
import saloon.app.android.data.repository.user.UsersRepositoryImpl
import java.util.*

class QuestionsItemKeyed(
    private val db: FirebaseFirestore,
    private val userId: String,
    private val questionsCollectionName: String
) : ItemKeyedDataSource<Long, Model>() {

    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Model>
    ) {
        db
            .collection(questionsCollectionName)
            .limit(params.requestedLoadSize.toLong())
            .orderBy("date", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                val questions = result.toObjects(Question::class.java)
                callback.onResult(questions.map {
                    Model().apply { setT(it) }
                })
            }.addOnFailureListener {
                Log.e("Questions_paging_init", it.message ?: it.toString())
            }
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Model>) {
        if (params.requestedLoadSize > 0)
            db
                .collection(questionsCollectionName)
                .limit(params.requestedLoadSize.toLong())
                .orderBy("date", Query.Direction.DESCENDING)
                .startAfter(params.key)
                .get()
                .addOnSuccessListener { result ->
                    val questions = result.toObjects(Question::class.java)
                    val items = mutableListOf<Model>()
                    for (question in questions) {
                        items.add(Model().apply { setT(question) })
                    }
                    callback.onResult(items)
                }
                .addOnFailureListener {
                    Log.e("Questions_paging_init", it.message ?: it.toString())
                }
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Model>) {
        if (params.requestedLoadSize > 0)
            db
                .collection(questionsCollectionName)
                .limit(params.requestedLoadSize.toLong())
                .orderBy("date", Query.Direction.DESCENDING)
                .endBefore(params.key)
                .get()
                .addOnSuccessListener { result ->

                    val questions = result.toObjects(Question::class.java)
                    val items = mutableListOf<Model>()
                    for (question in questions) {
                        items.add(Model().apply { setT(question) })
                    }
                    callback.onResult(items)
                }.addOnFailureListener {
                    Log.e("Questions_paging_init", it.message ?: it.toString())
                }

    }

    override fun getKey(item: Model) = (item.item as? Question)?.date ?: Date().time
}