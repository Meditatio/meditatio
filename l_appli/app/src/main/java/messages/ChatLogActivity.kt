package messages

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
//import androidx.recyclerview.widget.RecyclerView.findNestedRecyclerView
import com.example.meditatio_appli.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import models.ChatMessage
import models.User



class ChatLogActivity : AppCompatActivity() {

    companion object {
        val TAG = "ChatLog"
    }

    val adapter = GroupAdapter<GroupieViewHolder>()

    var toUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        val recycleview_chat_log = findViewById<RecyclerView>(R.id.recyclerview_chat_log)
        recycleview_chat_log.adapter = adapter

        toUser = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        supportActionBar?.title = toUser?.username

        //setupDummyData()
        listenForMessage()

        val send_button_chat_log = findViewById<Button>(R.id.send_button_chat_log)
        send_button_chat_log.setOnClickListener{
            Log.d(TAG, "Attempt to send message....")
            performSendMessage()
        }
    }

    private fun listenForMessage(){
        val fromId = FirebaseAuth.getInstance().uid
        val toId = toUser?.uid
        val ref = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId")
        ref.addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java)

                if (chatMessage != null){
                    Log.d(TAG, chatMessage.text)

                    if (chatMessage.fromId==FirebaseAuth.getInstance().uid){
                        val currentUser = LatestMessagesActivity.currentUser ?:return
                        adapter.add(ChatFromItem(chatMessage.text, currentUser))
                    }else{
                        if (toUser != null) {
                            adapter.add(ChatToItem(chatMessage.text, toUser!!))
                        } else {
                            Log.e("ChatLogActivity", "toUser is null")
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
            }
        })
    }

    private fun performSendMessage(){
        val edittext_chat_log = findViewById<TextView>(R.id.edittext_chat_log)
        val text = edittext_chat_log.text.toString()

        val fromId = FirebaseAuth.getInstance().uid
        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        val toId = user?.uid

        if (fromId == null) return

       // val reference = FirebaseDatabase.getInstance().getReference("/messages").push()
        val reference = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId").push()


        val toReference = FirebaseDatabase.getInstance().getReference("/user-messages/$toId/$fromId").push()

        val chatMessage = ChatMessage(reference.key!!, text, fromId, toId!!,
            System.currentTimeMillis() / 1000)
        reference.setValue(chatMessage)
            .addOnSuccessListener{
                Log.d(TAG, "saved our chat message: ${reference.key}")
            }
        toReference.setValue(chatMessage)
            .addOnSuccessListener {
                Log.d(TAG, "saved our chat message: ${reference.key}")
                edittext_chat_log.text = ""
                val recyclerview_chat_log = findViewById<RecyclerView>(R.id.recyclerview_chat_log)
                recyclerview_chat_log.scrollToPosition(adapter.itemCount -1)
            }
        toReference.setValue(chatMessage)
    }
}

class ChatFromItem(val text:String, val user: User): Item<GroupieViewHolder>()
{
    override fun bind(p0: GroupieViewHolder, p1: Int) {
        val textViewFromRow = p0.itemView.findViewById<TextView>(R.id.textView_from_row)
        textViewFromRow.text = text

        // load our user image into the star
        val uri = user.profileImageUrl
        val targetImageView = p0.itemView.findViewById<ImageView>(R.id.imageView_from_row)
        Picasso.get().load(uri).into(targetImageView)
    }
    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }
}
class ChatToItem(val text: String, val user: User): Item<GroupieViewHolder>()
{
    override fun bind(p0: GroupieViewHolder, p1: Int) {
        val textViewFromRow = p0.itemView.findViewById<TextView>(R.id.textView_to_row)
        textViewFromRow.text = text

        // load our user image into the star
        val uri = user.profileImageUrl
        val targetImageView = p0.itemView.findViewById<ImageView>(R.id.imageView_to_row)
        Picasso.get().load(uri).into(targetImageView)
    }

    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }
}