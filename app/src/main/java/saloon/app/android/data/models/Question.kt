package saloon.app.android.data.models

data class Question(
    var id: String = "",
    val title: String = "",
    val author: User? = null,
    val answers: List<Answer> = emptyList(),
    val imageUrl: String = "",
    val date: Long = 0L
)