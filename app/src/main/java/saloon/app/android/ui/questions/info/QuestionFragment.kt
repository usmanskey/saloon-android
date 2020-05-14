package saloon.app.android.ui.questions.info

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import saloon.app.android.R

class QuestionFragment : Fragment(R.layout.question_fragment) {

    private lateinit var viewModel: QuestionViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(QuestionViewModel::class.java)
    }
}