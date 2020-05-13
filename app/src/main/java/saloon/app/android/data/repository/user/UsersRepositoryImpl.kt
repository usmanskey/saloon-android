package saloon.app.android.data.repository.user

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import saloon.app.android.data.models.User
import saloon.app.android.data.repository.UsersRepository


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

    companion object {
        const val USERS_COLLECTION_NAME = "users"
    }
}