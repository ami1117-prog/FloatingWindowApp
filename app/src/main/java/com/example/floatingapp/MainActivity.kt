package com.example.floatingapp

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStart = findViewById<Button>(R.id.btnStart)
        val btnStop = findViewById<Button>(R.id.btnStop)

        btnStart.setOnClickListener {
            if (!Settings.canDrawOverlays(this)) {
                requestOverlayPermission()
            } else {
                startFloatingService()
            }
        }

        btnStop.setOnClickListener {
            stopService(Intent(this, FloatingWindowService::class.java))
        }
    }

    private fun requestOverlayPermission() {
        Toast.makeText(this, "لطفاً اجازه نمایش روی سایر اپ‌ها رو فعال کنید", Toast.LENGTH_LONG).show()
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:$packageName")
        )
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        if (Settings.canDrawOverlays(this)) {
        }
    }

    private fun startFloatingService() {
        val intent = Intent(this, FloatingWindowService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
    }
}
