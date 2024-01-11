package com.achsanit.movieapp.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.achsanit.movieapp.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.CREATED) {
                delay(2000L)

                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)

                finish()
            }
        }
    }
}