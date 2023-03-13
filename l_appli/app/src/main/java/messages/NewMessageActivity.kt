package messages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.meditatio_appli.R
import registerlogin.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

class NewMessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)

        supportActionBar?.title = "Select User"
        val recyclerview_newmessage = findViewById<RecyclerView>(R.id.recyclerview_newmessage)
        fetchUsers()
    }

    private fun fetchUsers()
    {
        val ref = FirebaseDatabase.getInstance().getReference("/users")
        ref.addListenerForSingleValueEvent(object: ValueEventListener{

            override fun onDataChange(p0: DataSnapshot) {
                val adapter = GroupAdapter<GroupieViewHolder>()

                p0.children.forEach{
                    Log.d("NewMessage", it.toString())
                    val user = it.getValue(User::class.java)
                    if (user != null)
                    {
                        adapter.add(UserItem(user))
                    }
                }
                adapter.setOnItemClickListener { item, view ->
                    val intent = Intent(view.context, ChatLogActivity::class.java)
                    startActivity(intent)
                }
                val recyclerview_newmessage = findViewById<RecyclerView>(R.id.recyclerview_newmessage)

                recyclerview_newmessage.adapter = adapter
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }
}

class UserItem(val user: User): Item<GroupieViewHolder>()
{
    override fun bind(p0: GroupieViewHolder, p1: Int)
    {
        p0.itemView.findViewById<TextView>(R.id.usernametextview_newmessage).text = user.username

        Picasso.get().load(user.profileImageUrl).into(p0.itemView.findViewById<ImageView>(R.id.imageView_newmessagerow))
    }

    override fun getLayout():Int
    {
        return R.layout.user_row_newmessage
    }
}
