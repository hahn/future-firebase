package id.husna.futurefirebaseapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
  private val TAG = "FirebaseApp"
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    getToken()
  }

  private fun getToken() {
    FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
      if (!task.isSuccessful) {
        Log.w(TAG, "Fetching FCM registration token failed", task.exception)
        return@addOnCompleteListener
      }

      val token = task.result
      Log.d(TAG, "message token: $token")
      Toast.makeText(this, "Token: $token", Toast.LENGTH_LONG).show()
    }

    FirebaseInstallations.getInstance().id.addOnCompleteListener { installationIdTask ->
      if (installationIdTask.isSuccessful) {
        Log.d(TAG, "Firebase Installations ID: ${installationIdTask.result}")
      } else {
        Log.e(TAG, "Unable to retrieve installations ID",
          installationIdTask.exception)
      }
    }
  }
}