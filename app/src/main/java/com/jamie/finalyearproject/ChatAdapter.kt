package com.jamie.finalyearproject
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(val messageList : ArrayList<Message>) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.chat_message, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(messageList[position])

    }

    override fun getItemCount(): Int {
        return messageList.size
    }


    class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView)
    {

        fun bindItems(message : Message)
        {
            val sender = itemView.findViewById(R.id.message_sender) as TextView
            val content  = itemView.findViewById(R.id.message_content) as TextView
            sender.text = message.name
            content.text = message.message
        }


    }

}