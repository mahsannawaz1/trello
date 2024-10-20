package com.example.trello_clone


import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class EditBoard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_board)

        var closeBtn = findViewById<ImageView>(R.id.close_icon)

        closeBtn.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    val intent = Intent(this, BoardMenu::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        var goToLabels = findViewById<LinearLayout>(R.id.lableGrid)

        goToLabels.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    val intent = Intent(this, Labels::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

    }
}

