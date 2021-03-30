package com.jamie.finalyearproject

import android.app.Dialog
import android.content.Context
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PlayedSongsDialog
{

    lateinit var recycler : RecyclerView



    fun build(context: Context,songs : ArrayList<Song>,limit : Int) : Dialog
    {
        val dialog = Dialog(context)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.played_songs_layout)
        recycler = dialog.findViewById<RecyclerView>(R.id.played_songs_cycler)

        val playedSongs = ArrayList<Song>()

        for(i in 0 until limit)
        {
            playedSongs.add(songs[i])
        }

        val exit = dialog.findViewById<ImageView>(R.id.leaderboard_close)
        exit.setOnClickListener {
            dialog.dismiss()

        }


        val adapter = PlayedSongsAdapter(playedSongs)
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context.applicationContext)
        recycler.layoutManager = mLayoutManager
        recycler.adapter = adapter



        return dialog
    }


}