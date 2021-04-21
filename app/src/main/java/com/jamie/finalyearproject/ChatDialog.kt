package com.jamie.finalyearproject

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.ArrayList


class ChatDialog
{

    lateinit var recycler: RecyclerView
    lateinit var sendButton : Button
    lateinit var text : EditText
    var myLog = ArrayList<Message>()
    var messages = ArrayList<Message>()
    var sortedList = ArrayList<Message>()




    fun Build(context : Context) : Dialog
    {
        val dialog = Dialog(context)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.chat_dialog)
        recycler = dialog.findViewById<RecyclerView>(R.id.played_songs_cycler)
        sendButton = dialog.findViewById<Button>(R.id.send_button)
        val closeButton = dialog.findViewById<ImageView>(R.id.win_dialog_close)
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context.applicationContext)
        recycler.layoutManager = mLayoutManager
        closeButton.setOnClickListener { dialog.dismiss() }
        text = dialog.findViewById(R.id.message_box)
        val adapter = ChatAdapter(displayChat())
        setAdapter(adapter)
        return dialog
    }

    fun setAdapter(adapter: ChatAdapter)
    {
        recycler.adapter = adapter
    }

    // Sorts the list from oldest to newest messages
    fun displayChat() : ArrayList<Message>
    {
        val temp = ArrayList<Message>()
        temp.addAll(messages)
        Log.i("Temp arraylist", temp.toString())

        sortedList.clear()

        if(messages.size > 1)
        {
          while (sortedList.size != messages.size)
          {
              var small = messages[0]

              for(i in temp)
              {
                  if(i.time < small.time)
                  {
                      small = i
                  }
              }

              Log.i("Message being added", small.toString())
              sortedList.add(small)


          }
        }
        else{
            sortedList = temp
        }

        messages.clear()
        messages.addAll(temp)

        return messages

    }

    fun notifyChange()
    {
        if(this::recycler.isInitialized)
        {
            recycler.adapter?.notifyDataSetChanged()
        }

    }


    




}