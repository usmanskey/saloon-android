package saloon.app.android.data.repository.user

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import saloon.app.android.data.models.QuestionId
import saloon.app.android.data.models.User
import saloon.app.android.data.repository.UsersRepository
import saloon.app.android.data.repository.questions.QuestionsRepositoryImpl


class UsersRepositoryImpl(private val db: FirebaseFirestore, private val auth: FirebaseAuth) :
    UsersRepository {

    override suspend fun getUser(id: String) =
        db.collection(USERS_COLLECTION_NAME)
            .document(id)
            .get()
            .await()
            .toObject(User::class.java)

    override suspend fun getUser() = db
        .collection(USERS_COLLECTION_NAME)
        .document(auth.uid!!)
        .get()
        .await()
        .toObject(User::class.java)

    override suspend fun saveUser(user: User) {
        db.collection(USERS_COLLECTION_NAME).document(user.id).set(user).await()
    }

    override suspend fun addQuestionToUser(questionId: QuestionId) {
        db.collection(USERS_COLLECTION_NAME)
            .document(Firebase.auth.uid!!)
            .collection(QuestionsRepositoryImpl.QUESTIONS_COLLECTION_NAME)
            .add(questionId)
            .await()
    }

    companion object {
        const val USERS_COLLECTION_NAME = "users"
    }
}