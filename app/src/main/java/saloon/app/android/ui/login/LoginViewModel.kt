package saloon.app.android.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import saloon.app.android.data.models.User
import saloon.app.android.data.repository.UsersRepository

class LoginViewModel(private val usersRepository: UsersRepository) : ViewModel() {

    fun saveUser(user: User) = viewModelScope.launch {
        usersRepository.saveUser(user)
    }
}