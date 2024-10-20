package com.example.trello_clone


import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var logoutBtn = findViewById<ImageView>(R.id.menu_icon)
        logoutBtn.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        var btn1 = findViewById<ImageView>(R.id.workspace_1)

        btn1.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    val intent = Intent(this, BoardMenu::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        var btn2 = findViewById<ConstraintLayout>(R.id.add_Card)
        btn2.setOnTouchListener { view, motionEvent ->
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

