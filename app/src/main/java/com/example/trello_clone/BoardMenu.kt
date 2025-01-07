package com.example.trello_clone

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class BoardMenu : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var boardsRecyclerView: RecyclerView
    private lateinit var boardsAdapter: BoardItemAdapter
    private var boardsList: MutableList<BoardItem> = mutableListOf()


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
        // Initialize tickIcon
        tickIcon = findViewById(R.id.done)
        // Initialize Firebase Database
        database = FirebaseDatabase.getInstance().reference

        // Initialize Firebase Database Reference
        database = FirebaseDatabase.getInstance().reference

        // Initialize RecyclerView
        boardsRecyclerView = findViewById(R.id.cardsRecyclerView)
        boardsRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        boardsAdapter = BoardItemAdapter(boardsList) { boardItem ->
            // Open modal for the specific board
            openAddCardModal(boardItem)
        }
        boardsRecyclerView.adapter = boardsAdapter

        // Fetch boards data
        fetchBoards()

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
        filterIcon = findViewById(R.id.filter_icon)
        notificationIcon = findViewById(R.id.notification_icon)
        optionIcon = findViewById(R.id.option_icon)

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

    private fun fetchBoards() {
        database.child("boards").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                boardsList.clear()
                if (snapshot.exists()) {
                    for (boardSnapshot in snapshot.children) {
                        val boardId = boardSnapshot.key ?: "" // Get the board ID
                        val boardName = boardSnapshot.child("name").getValue(String::class.java) ?: ""
                        val boardImage = boardSnapshot.child("image").getValue(String::class.java) ?: ""
                        val cards = mutableListOf<CardItem>()

                        // Fetch cards for this board
                        val cardsSnapshot = boardSnapshot.child("cards")
                        for (card in cardsSnapshot.children) {
                            val cardId = card.key ?: ""
                            val cardName = card.child("name").getValue(String::class.java) ?: ""
                            cards.add(CardItem(cardId, cardName))
                        }

                        println("Board ID: $boardId, Name: $boardName, Cards: ${cards.size}")
                        boardsList.add(BoardItem(boardId, boardName, boardImage, cards))
                    }
                    boardsAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@BoardMenu, "No boards found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@BoardMenu, "Error fetching data: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun addCardToDatabase(boardItem: BoardItem, cardName: String) {
        val boardId = boardItem.boardId // boardId from the BoardItem

        val cardId = database.child("boards").child(boardId).child("cards").push().key
        if (cardId != null) {
            val cardData = mapOf(
                "id" to cardId,
                "name" to cardName
            )
            database.child("boards").child(boardId).child("cards").child(cardId).setValue(cardData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Card added successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to add card", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun openAddCardModal(boardItem: BoardItem) {
        modalView.visibility = View.VISIBLE

        // Set up the modal's input field
        val inputField: EditText = modalView.findViewById(R.id.inputField)
        val sendButton: ImageView = modalView.findViewById(R.id.sendButton)

        // Clear previous input
        inputField.text.clear()

        // Handle Add Card action
        sendButton.setOnClickListener {
            val cardName = inputField.text.toString().trim()
            if (cardName.isNotEmpty()) {
                addCardToDatabase(boardItem, cardName)
                modalView.visibility = View.GONE // Close modal after adding
            } else {
                Toast.makeText(this, "Please enter a card name", Toast.LENGTH_SHORT).show()
            }
        }

        // Set up the Close button inside the modal
        val closeButton: ImageView = modalView.findViewById(R.id.topCloseButton)
        closeButton.setOnClickListener {
            modalView.visibility = View.GONE
        }
    }


}




data class CardItem(
    val id: String,
    val name: String
)

class CardAdapter(private val cards: List<CardItem>) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardName: TextView = itemView.findViewById(R.id.card_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card_simple, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = cards[position]
        holder.cardName.text = card.name
    }

    override fun getItemCount() = cards.size
}



data class BoardItem(
    val boardId: String,
    val name: String,
    val image: String,
    val cards: List<CardItem> = emptyList() // Default to an empty list
)

class BoardItemAdapter(
    private val boards: List<BoardItem>,
    private val onAddCardClicked: (BoardItem) -> Unit
) : RecyclerView.Adapter<BoardItemAdapter.BoardViewHolder>() {

    inner class BoardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val boardName: TextView = itemView.findViewById(R.id.cardName)
        val boardImage: ImageView = itemView.findViewById(R.id.iv_card_image)
        val addCardButton: TextView = itemView.findViewById(R.id.tv_add_card) // Add Card Button
        val cardsRecyclerView: RecyclerView = itemView.findViewById(R.id.rv_cards) // Nested RecyclerView for cards
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return BoardViewHolder(view)
    }

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        val board = boards[position]

        holder.boardName.text = board.name
        Glide.with(holder.itemView.context)
            .load(if (board.image.isEmpty()) R.drawable.default_board else board.image)
            .placeholder(R.drawable.default_board)
            .into(holder.boardImage)

        // Navigate to EditBoard on card click
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, EditBoard::class.java)
            intent.putExtra("BOARD_ID", board.boardId) // Pass the Board ID
            context.startActivity(intent)
        }

        // Handle Add Card button click
        holder.addCardButton.setOnClickListener {
            onAddCardClicked(board)
        }

        // Setup the nested RecyclerView for cards
        val cardsAdapter = CardAdapter(board.cards) // Pass the list of cards for this board
        holder.cardsRecyclerView.layoutManager = LinearLayoutManager(holder.itemView.context)
        holder.cardsRecyclerView.adapter = cardsAdapter
    }

    override fun getItemCount() = boards.size

}

