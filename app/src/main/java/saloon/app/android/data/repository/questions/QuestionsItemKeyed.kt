package saloon.app.android.data.repository.questions

import android.util.Log
import androidx.paging.ItemKeyedDataSource
import com.google.firebase.firestore.FirebaseFirestore
import saloon.app.android.data.models.Model
import saloon.app.android.data.models.Question
import saloon.app.android.data.models.User
import saloon.app.android.data.repository.user.UsersRepositoryImpl

class QuestionsItemKeyed(
    private val db: FirebaseFirestore,
    private val userId: String,
    private val questionsCollectionName: String
) : ItemKeyedDataSource<Long, Model>() {

    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Model>
    ) {
        db.collection(UsersRepositoryImpl.USERS_COLLECTION_NAME)
            .document(userId)
            .collection(questionsCollectionName)
            .limit(params.requestedLoadSize.toLong())
            .orderBy("date")
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

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Model>) {
        db.collection(UsersRepositoryImpl.USERS_COLLECTION_NAME)
            .document(userId)
            .collection(questionsCollectionName)
            .limit(params.requestedLoadSize.toLong())
            .orderBy("date")
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
    }

    override fun getKey(item: Model) = (item.item as? Question)?.date ?: 0L
}