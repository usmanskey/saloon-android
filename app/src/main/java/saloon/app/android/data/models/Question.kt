package saloon.app.android.data.models

data class Question(
    var id: String = "",
    val title: String = "",
    val author: User = User("", "", "", "", 0, 0),
    val answers: List<Answer> = emptyList(),
    val imageUrl: String = ""
)