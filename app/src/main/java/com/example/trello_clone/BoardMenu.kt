package com.example.trello_clone


import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity


class BoardMenu : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_menu)

        var goToHome = findViewById<ImageView>(R.id.menu_icon)
        goToHome.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        var goToEditBoard = findViewById<LinearLayout>(R.id.card1_detail2)
        goToEditBoard.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    val intent = Intent(this, EditBoard::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}

