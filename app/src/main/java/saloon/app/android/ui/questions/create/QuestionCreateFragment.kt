package saloon.app.android.ui.questions.create

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import saloon.app.android.R

class QuestionCreateFragment() : Fragment(R.layout.create_question_fragment) {

    private lateinit var viewModel: QuestionCreateViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(QuestionCreateViewModel::class.java)

    }
}