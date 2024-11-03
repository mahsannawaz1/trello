package com.example.trello_clone

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_profile)

            var logoutBtn = findViewById<ImageView>(R.id.exit_icon)

            logoutBtn.setOnTouchListener { view, motionEvent ->
                when (motionEvent.action) {
                    MotionEvent.ACTION_DOWN -> {
                        val intent = Intent(this, SignInActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    else -> false
                }
            }

            var goToBoards = findViewById<LinearLayout>(R.id.boards_section)
            goToBoards.setOnTouchListener { view, motionEvent ->
                when (motionEvent.action) {
                    MotionEvent.ACTION_DOWN -> {
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    else -> false
                }
            }

            var goToCards = findViewById<LinearLayout>(R.id.cards_section)
            goToCards.setOnTouchListener { view, motionEvent ->
                when (motionEvent.action) {
                    MotionEvent.ACTION_DOWN -> {
                        val intent = Intent(this, BoardMenu::class.java)
                        startActivity(intent)
                        true
                    }
                    else -> false
                }
            }

    }
}

