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
    private val user: User,
    private val questionsCollectionName: String
) : ItemKeyedDataSource<String, Model>() {

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<Model>
    ) {
        db.collection(UsersRepositoryImpl.USERS_COLLECTION_NAME)
            .document(user.id)
            .collection(questionsCollectionName)
            .limit(params.requestedLoadSize.toLong())
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

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<Model>) {
        db.collection(UsersRepositoryImpl.USERS_COLLECTION_NAME)
            .document(user.id)
            .collection(questionsCollectionName)
            .limit(params.requestedLoadSize.toLong())
            .orderBy("id")
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

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<Model>) {
    }

    override fun getKey(item: Model) = (item.item as? Question)?.id ?: ""
}