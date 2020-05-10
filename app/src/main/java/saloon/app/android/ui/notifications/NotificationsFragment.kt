package saloon.app.android.ui.notifications

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import saloon.app.android.R

class NotificationsFragment : Fragment(R.layout.notifications_fragment) {
    private lateinit var viewModel: NotificationsViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)


    }
}