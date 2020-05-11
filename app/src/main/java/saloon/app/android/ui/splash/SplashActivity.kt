package saloon.app.android.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import saloon.app.android.ui.MainActivity
import saloon.app.android.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(
            Intent(
                this, if (GoogleSignIn.getLastSignedInAccount(this) != null) {
                    MainActivity::class.java
                } else {
                    LoginActivity::class.java
                }
            )
        )
    }
}