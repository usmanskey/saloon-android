package saloon.app.android.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import saloon.app.android.data.repository.UsersRepository

class LoginViewModelProvider(private val usersRepository: UsersRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        LoginViewModel(usersRepository) as T
}