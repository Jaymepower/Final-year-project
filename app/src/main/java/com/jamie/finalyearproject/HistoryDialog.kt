package com.jamie.finalyearproject

import android.app.Dialog
import android.content.Context
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class HistoryDialog
{


    fun build(context: Context, games : ArrayList<GameLog>) : Dialog{

        val dialog = Dialog(context)
        dialog.setContentView(R.layout.history_dialog)

        val recycler = dialog.findViewById<RecyclerView>(R.id.history_recycler)

        val close = dialog.findViewById<ImageView>(R.id.log_close_button)
        close.setOnClickListener {
            dialog.dismiss()
        }

        val adapter = HistoryAdapter(games)
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context.applicationContext)
        recycler.layoutManager = mLayoutManager
        recycler.adapter = adapter

        return dialog
    }

}