package id.husna.futurefirebaseapp.fcm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import id.husna.futurefirebaseapp.R

/**
 *  Created by hanhan.firmansyah on 20/09/21
 */
class MyMessageService: FirebaseMessagingService() {
  private val TAG = "FirebaseApp"
  private var notificationManager: NotificationManagerCompat? = null
  private var notificationChannel: NotificationChannel? = null
  private val defaultChannel = "fcm_default_channel"
  override fun onNewToken(token: String) {
    super.onNewToken(token)
    Log.d(TAG, "token: $token")
  }

  override fun onMessageReceived(message: RemoteMessage) {
    super.onMessageReceived(message)
    notificationManager = NotificationManagerCompat.from(this)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      notificationChannel =
        NotificationChannel(defaultChannel, "Future firebase", NotificationManager.IMPORTANCE_DEFAULT)
      notificationChannel?.lightColor = Color.RED
    }
    notificationChannel?.let {
      notificationManager?.createNotificationChannel(it)
    }
    if (message.data.isNotEmpty()) {
      Log.d(TAG, "message received: ${message.data}")
      val notification = buildNotification(message)
      notificationManager?.notify(123, notification)
    } else {
      Log.d(TAG, "message data empty")
    }
    message.notification?.let {
      Log.d(TAG, "notification body: ${it.body}")
    }
  }

  private fun buildNotification(message: RemoteMessage): Notification {
    return NotificationCompat.Builder(applicationContext, defaultChannel)
      .setContentTitle(message.data["title"])
      .setContentText(message.data["message"])
      .setSmallIcon(R.drawable.ic_notification)
      .build()
  }


}