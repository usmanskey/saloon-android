package saloon.app.android.data.models

data class User(
    val id: String = "",
    val name: String = "",
    val username: String = "",
    val bio: String = "",
    val rate: Int = 0,
    val answers: Int = 0
)