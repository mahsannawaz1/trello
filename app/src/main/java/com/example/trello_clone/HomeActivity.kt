package com.example.trello_clone

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class HomeActivity : AppCompatActivity() {

    private lateinit var modalView: View

    private lateinit var modalView1: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Set up logout navigation button
        val logoutBtn = findViewById<ImageView>(R.id.menu_icon)
        logoutBtn.setOnTouchListener { _, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                true
            } else {
                false
            }
        }

        // Set up workspace navigation button
        val btn1 = findViewById<ImageView>(R.id.workspace_1)
        btn1.setOnTouchListener { _, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                val intent = Intent(this, BoardMenu::class.java)
                startActivity(intent)
                true
            } else {
                false
            }
        }

        // Set up add card navigation button
        val btn2 = findViewById<ConstraintLayout>(R.id.add_Card)
        btn2.setOnTouchListener { _, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                val intent = Intent(this, BoardMenu::class.java)
                startActivity(intent)
                true
            } else {
                false
            }
        }

        // Set up notification navigation button
        val btn3 = findViewById<ImageView>(R.id.notification_icon)
        btn3.setOnTouchListener { _, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                val intent = Intent(this, NotificationActivity::class.java)
                startActivity(intent)
                true
            } else {
                false
            }
        }

        // Inflate the modal layout and add it to the main content view
        val rootView = findViewById<ViewGroup>(android.R.id.content) // Ensure this is a ViewGroup
        modalView = LayoutInflater.from(this).inflate(R.layout.activity_add_card, rootView, false)

        // Initially, hide the modal
        modalView.visibility = View.GONE
        rootView.addView(modalView)

        // Set up the Open Modal button click listener
        val openModalButton = findViewById<Button>(R.id.btn_add_Card)
        openModalButton.setOnClickListener {
            modalView.visibility = View.VISIBLE
        }

        // Set up the Close button inside the modal
        val topCloseButton: ImageView = modalView.findViewById(R.id.topCloseButton)
        topCloseButton.setOnClickListener {
            modalView.visibility = View.GONE
        }

        // Hide the modal when clicking outside the main modal area (this will close the modal)
        modalView.setOnClickListener {
            modalView.visibility = View.GONE
        }



        // Inflate the modal layout and add it to the main content view
        val rootView1 = findViewById<ViewGroup>(android.R.id.content) // Ensure this is a ViewGroup
        modalView1 = LayoutInflater.from(this).inflate(R.layout.activity_change_location, rootView, false)

        // Initially, hide the modal
        modalView1.visibility = View.GONE
        rootView1.addView(modalView1)

        // Set up the Open Modal button click listener
        val openModalButton1 = findViewById<ImageView>(R.id.edit_icon)
        openModalButton1.setOnClickListener {
            modalView1.visibility = View.VISIBLE
        }

        // Set up the Close button inside the modal
        val topCloseButton2: ImageView = modalView1.findViewById(R.id.topCloseButton)
        topCloseButton2.setOnClickListener {
            modalView1.visibility = View.GONE
        }

        // Hide the modal when clicking outside the main modal area (this will close the modal)
        modalView1.setOnClickListener {
            modalView1.visibility = View.GONE
        }


        val dropdownMenu = modalView1.findViewById<Spinner>(R.id.boardMenu)

        // Access the string array from resources
        val board_options = resources.getStringArray(R.array.board_dropdown_options)

        // Create an ArrayAdapter using the string array and a default spinner layout
        val adapter = ArrayAdapter(this, R.layout.dropdown_item, board_options)

        // Set the adapter to the spinner
        dropdownMenu.adapter = adapter
        // Set a default selection (optional)
        dropdownMenu.setSelection(0) // 0 for "Option 1"



        val dropdownMenu1 = modalView1.findViewById<Spinner>(R.id.listMenu)

        // Access the string array from resources
        val list_options = resources.getStringArray(R.array.list_dropdown_options)

        // Create an ArrayAdapter using the string array and a default spinner layout
        val adapter1 = ArrayAdapter(this, R.layout.dropdown_item, list_options)

        // Set the adapter to the spinner
        dropdownMenu1.adapter = adapter1
        // Set a default selection (optional)
        dropdownMenu1.setSelection(0) // 0 for "Option 1"
    }
}
