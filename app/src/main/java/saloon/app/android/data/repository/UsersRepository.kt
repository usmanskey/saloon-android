package saloon.app.android.data.repository

import saloon.app.android.data.models.User

interface UsersRepository {

    suspend fun getUser(id: String): User?

    suspend fun saveUser(user: User)
}