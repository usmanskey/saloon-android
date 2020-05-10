package saloon.app.android.data.models

data class Question(
    val id: String,
    val title: String,
    val author: Author,
    val answers: List<Answer>,
    val imageUrl: String
)