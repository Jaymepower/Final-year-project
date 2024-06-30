package com.jamie.musicbingo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jamie.musicbingo.R
import com.jamie.musicbingo.data.User


class UserAdapter(val users : ArrayList<User>) : RecyclerView.Adapter<UserAdapter.ViewHolder>()
{


    class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView)
    {

        fun bindItems(user : User)
        {
            val name = itemView.findViewById<TextView>(R.id.user_name)
            val score = itemView.findViewById<TextView>(R.id.user_score)
            val line = itemView.findViewById<TextView>(R.id.user_line)

            name.text = user.displayName
            score.text = user.score.toString()
            line.text = user.lines.toString()


        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(users[position])
    }

    override fun getItemCount(): Int {
        return users.size
    }
}