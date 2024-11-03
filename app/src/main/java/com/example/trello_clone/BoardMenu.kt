package com.example.trello_clone


import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.TypedValueCompat.dpToPx
import androidx.core.view.marginLeft


class BoardMenu : AppCompatActivity() {

    private lateinit var modalView: View

    private lateinit var menuIcon: ImageView
    private lateinit var closeIcon: ImageView
    private lateinit var filterIcon: ImageView
    private lateinit var notificationIcon: ImageView
    private lateinit var optionIcon: ImageView
    private lateinit var tickIcon: ImageView

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

        // Inflating the modal layout and add it to the main content view
        val rootView = findViewById<ViewGroup>(android.R.id.content)
        modalView = LayoutInflater.from(this).inflate(R.layout.activity_add_card, rootView, false)

        // Initially, hide the modal
        modalView.visibility = View.GONE
        rootView.addView(modalView)

        // Set up the Open Modal button click listener
        val openModalButton = findViewById<LinearLayout>(R.id.card1_detail5)
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



        menuIcon = findViewById(R.id.menu_icon)
        closeIcon = findViewById(R.id.close_search)
        filterIcon = findViewById(R.id.filter_icon)
        notificationIcon = findViewById(R.id.notification_icon)
        optionIcon = findViewById(R.id.option_icon)
        tickIcon = findViewById(R.id.done)

        val headingBtn = findViewById<EditText>(R.id.board_heading)

        headingBtn.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                OnOpenSearchBar()
            }
            else{
                OnCloseSearchBar()
            }
        }

        menuIcon.setOnClickListener {
            OnCloseSearchBar()
        }
        tickIcon.setOnClickListener {
            OnCloseSearchBar()
            headingBtn.clearFocus()
        }
    }

    private fun OnOpenSearchBar() {


        filterIcon.visibility = View.GONE          // Hide filterIcon
        notificationIcon.visibility = View.GONE  // Hide notificationIcon
        optionIcon.visibility = View.GONE        // Hide optionIcon
        tickIcon.visibility = View.VISIBLE          // Show tickIcon
    }

    private fun OnCloseSearchBar() {
        filterIcon.visibility = View.VISIBLE          // Show filterIcon
        notificationIcon.visibility = View.VISIBLE  // Show notificationIcon
        optionIcon.visibility = View.VISIBLE        // Show optionIcon
        tickIcon.visibility = View.GONE          // Hide tickIcon
    }


}

