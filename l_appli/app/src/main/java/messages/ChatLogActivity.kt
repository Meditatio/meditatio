package messages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.meditatio_appli.R
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

class ChatLogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        supportActionBar?.title = "Chat Log"

        val adapter = GroupAdapter<GroupieViewHolder>()
        adapter.add(ChatItem())
        adapter.add(ChatItem())
        adapter.add(ChatItem())
        adapter.add(ChatItem())
        val recycler = findViewById<RecyclerView>(R.id.recyclerview_chat_log)
        recycler.adapter = adapter
    }
}

class ChatItem: Item<GroupieViewHolder>()
{
    override fun bind(p0: GroupieViewHolder, p1: Int) {
    }

    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }
}
