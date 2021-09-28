package id.husna.futurefirebaseapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging

class MainActivity : AppCompatActivity() {
  private val TAG = "FirebaseApp"
  private lateinit var firebaseAnalytics: FirebaseAnalytics

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    firebaseAnalytics = Firebase.analytics
    setContentView(R.layout.activity_main)
    getToken()
    subscribeTopicNotification()
    crashMe()
    testAnalytics()
  }

  private fun getToken() {
//    FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
//      if (!task.isSuccessful) {
//        Log.w(TAG, "Fetching FCM registration token failed", task.exception)
//        return@addOnCompleteListener
//      }
//
//      val token = task.result
//      Log.d(TAG, "message token: $token")
//      Toast.makeText(this, "Token: $token", Toast.LENGTH_LONG).show()
//    }

    FirebaseInstallations.getInstance().id.addOnCompleteListener { installationIdTask ->
      if (installationIdTask.isSuccessful) {
        Log.d(TAG, "Firebase Installations ID: ${installationIdTask.result}")
      } else {
        Log.e(TAG, "Unable to retrieve installations ID",
          installationIdTask.exception)
      }
    }
  }

  private fun subscribeTopicNotification() {
    Firebase.messaging.subscribeToTopic("topic1").addOnCompleteListener { task ->
      val message = if (task.isSuccessful) {
        "topic subscribed"
      } else {
        "topic failed to subscribe"
      }
      Log.d(TAG, "message subscribe: $message")
    }
  }

  private fun crashMe() {
    val btn = findViewById<Button>(R.id.button)
    btn.setOnClickListener {
      throw RuntimeException("Test Crash")
    }
  }

  private fun testAnalytics() {
    val btn2 = findViewById<Button>(R.id.button2)
    btn2.setOnClickListener {
      firebaseAnalytics.logEvent("click_button") {
        param("button_name", "Analytics")
      }
      Toast.makeText(this, "button clicked", Toast.LENGTH_LONG).show()
    }
  }

}