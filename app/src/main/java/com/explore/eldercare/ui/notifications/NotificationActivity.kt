package com.explore.eldercare.ui.notifications

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.Dialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.provider.Settings
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.explore.eldercare.R
import com.explore.eldercare.databinding.ActivityNotificationBinding
import com.explore.eldercare.ui.notifications.data.Reminder
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.flow.collect

class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding
    private lateinit var viewModel: NotificationsViewModel
    private lateinit var adapter: RemindersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        createNotificationChannel()

        binding.fabCreateReminder.setOnClickListener {
            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.set_reminder_dialog, null)
            val btnCreate = view.findViewById<AppCompatButton>(R.id.btn_create)
            val message = view.findViewById<TextInputEditText>(R.id.message)
            val time = view.findViewById<TimePicker>(R.id.timePicker)
            val toggleGroup = view.findViewById<MaterialButtonToggleGroup>(R.id.toggleButton)
            val frequency = if (toggleGroup.checkedButtonId == R.id.button1) "once" else "daily"
            dialog.setCancelable(true)
            dialog.setContentView(view)
            dialog.show()
            btnCreate.setOnClickListener {
                if (checkNotificationPermission(applicationContext)) scheduleNotification(
                    frequency,
                    message.text.toString(),
                    time
                )
            }
        }
        viewModel  = NotificationsViewModel()
        val list = viewModel.getAllReminders

        adapter = RemindersAdapter()
        adapter.setReminders(list)
        binding.rvReminder.adapter = adapter

    }

    @SuppressLint("ScheduleExactAlarm")
    private fun scheduleNotification(frequency : String,message: String,timePicker : TimePicker){
        val intent = Intent(applicationContext,BroadCastReceiver::class.java)

        intent.putExtra(messageExtra,message)

        val pendingIntent = PendingIntent.getBroadcast(// idi kuda
            applicationContext,
            121,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        //val time = getTime()
        val timeNow = System.currentTimeMillis()
        val calendar: Calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY,timePicker.hour)
            set(Calendar.MINUTE, timePicker.minute)
        }
        if(frequency == "once"){
//            calendar.set(Calendar.YEAR,2024)
//            calendar.set(Calendar.MONTH,System.currentTimeMillis().)
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,//idi endo
                calendar.timeInMillis,
                pendingIntent
            )
        }
        else{
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                24*60*60*1000,
                pendingIntent
            )
        }

        viewModel.addReminder(Reminder(message = message, frequency = frequency, time = calendar.timeInMillis.toString()))
        Toast.makeText(applicationContext,"At: notification is set with title :$title and message; $message",Toast.LENGTH_SHORT).show()
    }

//    private fun getTime(): Long {
//        val minute = binding.timePicker.minute
//        val hour = binding.timePicker.hour
//        val day = binding.datePicker.dayOfMonth
//        val month = binding.datePicker.month
//        val year = binding.datePicker.year
//
//        val calendar = Calendar.getInstance()
//        calendar.set(year,month,day,hour,minute)
//
//        return calendar.timeInMillis
//    }

    private fun checkNotificationPermission(context: Context): Boolean {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val isEnabled = notificationManager.areNotificationsEnabled()

        if (!isEnabled) {

            val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
            context.startActivity(intent)

            return false
        }

        return true
    }

    private fun createNotificationChannel() {

        val name = "Reminder Channel"
        val description = "A channel for all the notification of reminders"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID,name,importance)
        channel.description = description
        channel.enableVibration(true)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }
}