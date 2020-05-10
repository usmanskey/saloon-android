package saloon.app.android.data.models

data class Question(
    val id: String,
    val title: String,
    val author: Author,
    val answers: List<Answer>,
    val imageUrl: String
)

data class Author(val id: String, val name: String)

data class Answer(
    val id: String,
    val answer: String,
    val rate: Int,
    val author: Author,
    val questionId: String
)