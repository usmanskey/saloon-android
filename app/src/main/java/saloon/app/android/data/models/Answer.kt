package saloon.app.android.data.models

data class Answer(
    val id: String,
    val answer: String,
    val rate: Int,
    val author: Author,
    val questionId: String
)