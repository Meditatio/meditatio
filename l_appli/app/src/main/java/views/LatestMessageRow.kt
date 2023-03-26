package views

import android.widget.ImageView
import android.widget.TextView
import com.example.meditatio_appli.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import models.ChatMessage
import models.User

class LatestMessageRow(val chatMessage: ChatMessage) : Item<GroupieViewHolder>(){

    override fun bind(p0: GroupieViewHolder, p1: Int) {
        val message_textview_latest_message = p0.itemView.findViewById<TextView>(R.id.message_textview_latest_message)
        message_textview_latest_message.text = chatMessage.text

        val username_textview_latest_message = p0.itemView.findViewById<TextView>(R.id.username_textview_latest_message)


        val chatPartnerId:String
        if (chatMessage.fromId == FirebaseAuth.getInstance().uid)
        {
            chatPartnerId = chatMessage.toId
        }
        else
        {
            chatPartnerId = chatMessage.fromId
        }
        val ref = FirebaseDatabase.getInstance().getReference("/users/$chatPartnerId")
        ref.addListenerForSingleValueEvent(object: ValueEventListener
        {
            override fun onDataChange(p2: DataSnapshot)
            {
                val user = p2.getValue(User::class.java)
                username_textview_latest_message.text = user?.username

                val targetImageView = p0.itemView.findViewById<ImageView>(R.id.imageview_latest_message)
                Picasso.get().load(user?.profileImageUrl).into(targetImageView)
            }
            override fun onCancelled(p2: DatabaseError)
            {

            }
        })

    }
    override fun getLayout(): Int {
        return R.layout.latest_message_row
    }
}