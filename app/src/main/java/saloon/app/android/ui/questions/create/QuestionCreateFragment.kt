package saloon.app.android.ui.questions.create

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.create_question_fragment.*
import kotlinx.coroutines.launch
import org.koin.ext.scope
import saloon.app.android.R
import saloon.app.android.data.models.Question
import saloon.app.android.data.repository.questions.QuestionsItemKeyed
import saloon.app.android.data.repository.questions.QuestionsRepositoryImpl
import java.util.concurrent.Executors

class QuestionCreateFragment : Fragment(R.layout.create_question_fragment) {

    private lateinit var viewModel: QuestionCreateViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(
            this, QuestionCreateViewModelProvider(
                QuestionsRepositoryImpl(
                    QuestionsItemKeyed(
                        Firebase.firestore,
                        Firebase.auth.uid!!,
                        QuestionsRepositoryImpl.QUESTIONS_COLLECTION_NAME
                    ),
                    Executors.newSingleThreadExecutor(),
                    ContextCompat.getMainExecutor(context)
                )
            )
        ).get(QuestionCreateViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        create_question_button.setOnClickListener {

            lifecycleScope.launch {
                viewModel.createQuestion(Question(title = question_title.text.toString()))
            }
        }
    }
}