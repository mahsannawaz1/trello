package com.example.trello_clone


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class EditBoard : AppCompatActivity() {

    private lateinit var scrollView: ScrollView
    private lateinit var modalView: View
    private lateinit var modalView1: View

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


        // Inflating the modal layout and add it to the main content view
        val rootView = findViewById<ViewGroup>(android.R.id.content)
        modalView = LayoutInflater.from(this).inflate(R.layout.activity_members, rootView, false)

        // Initially, modal is hidden
        modalView.visibility = View.GONE
        rootView.addView(modalView)

        // Setting up the Open Modal button click listener
        val openModalButton = findViewById<LinearLayout>(R.id.members_btn)
        openModalButton.setOnClickListener {
            modalView.visibility = View.VISIBLE
        }

        // Setting up the Close button inside the modal
        val topCloseButton: TextView = modalView.findViewById(R.id.done_button)
        topCloseButton.setOnClickListener {
            modalView.visibility = View.GONE
        }

        // Hiding the modal when clicking outside the main modal area (this will close the modal)
        modalView.setOnClickListener {
            modalView.visibility = View.GONE
        }



        // Inflating the modal layout and add it to the main content view
        val rootView1 = findViewById<ViewGroup>(android.R.id.content)
        modalView1 = LayoutInflater.from(this).inflate(R.layout.activity_attachments, rootView1, false)

        // Initially, modal is hidden
        modalView1.visibility = View.GONE
        rootView.addView(modalView1)

        // Setting up the Open Modal button click listener
        val openModalButton1 = findViewById<LinearLayout>(R.id.add_attachment_btn)
        openModalButton1.setOnClickListener {
            modalView1.visibility = View.VISIBLE
        }
        val openModalButton2 = findViewById<ImageView>(R.id.link_image)
        openModalButton2.setOnClickListener {
            modalView1.visibility = View.VISIBLE
        }

        // Hiding the modal when clicking outside the main modal area (this will close the modal)
        modalView1.setOnClickListener {
            modalView1.visibility = View.GONE
        }


        scrollView = findViewById(R.id.scrollViewer)
        var addItemEditText: EditText = findViewById(R.id.addchecklist_input)

        val checklistBtn: LinearLayout = findViewById(R.id.addChecklist_btn)
        checklistBtn.setOnClickListener {
            // Scroll to the EditText and focus it
            scrollToView(addItemEditText)
            addItemEditText.requestFocus()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(addItemEditText, InputMethodManager.SHOW_IMPLICIT)
        }

    }
    private fun scrollToView(view: View) {
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        val y = location[1]
        scrollView.smoothScrollTo(0, y)
    }
}

