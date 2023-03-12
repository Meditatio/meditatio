package com.example.meditatio_appli

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

class NewMessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)

        supportActionBar?.title = "Select User"
        val recyclerview_newmessage = findViewById<RecyclerView>(R.id.recyclerview_newmessage)

        val adapter = GroupAdapter<GroupieViewHolder>()
        adapter.add(UserItem())
        adapter.add(UserItem())
        adapter.add(UserItem())



        recyclerview_newmessage.adapter = adapter
    }
}

class UserItem: Item<GroupieViewHolder>()
{
    override fun bind(p0: GroupieViewHolder, p1: Int)
    {
        //will be called in our list for each user object later on...
    }

    override fun getLayout():Int
    {
        return R.layout.user_row_newmessage
    }
}

/*
class CustomAdapter:RecyclerView.Adapter<ViewHolder>
{
    override fun OnBindViewHolder(p0:, p1:Int)
    {

    }
}*/