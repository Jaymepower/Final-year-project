package com.jamie.musicbingo.data

import android.app.Dialog
import android.content.Context
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jamie.musicbingo.Message
import com.jamie.musicbingo.R
import com.jamie.musicbingo.adapters.ChatAdapter
import kotlin.collections.ArrayList


class ChatDialog {

    lateinit var recycler: RecyclerView
    lateinit var sendButton: Button
    lateinit var text: EditText
    var myLog = ArrayList<Message>()
    var messages = ArrayList<Message>()

    lateinit var dialog: Dialog


    fun Build(context: Context): Dialog {
        dialog = Dialog(context)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.chat_dialog)
        recycler = dialog.findViewById(R.id.played_songs_cycler)
        sendButton = dialog.findViewById(R.id.send_button)
        val closeButton = dialog.findViewById<ImageView>(R.id.win_dialog_close)
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context.applicationContext)
        recycler.layoutManager = mLayoutManager
        closeButton.setOnClickListener { dialog.dismiss() }
        text = dialog.findViewById(R.id.message_box)
        val adapter = ChatAdapter(displayChat())
        setAdapter(adapter)
        return dialog
    }

    private fun setAdapter(adapter: ChatAdapter) {
        recycler.adapter = adapter
    }

    private fun displayChat(): ArrayList<Message> {
        messages.sortBy { it.time }
        return messages
    }

    fun notifyChange() {
        if (this::recycler.isInitialized) {
            recycler.adapter?.notifyDataSetChanged()
        }
    }


}