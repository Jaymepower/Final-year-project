package com.jamie.finalyearproject

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class PlayedSongsAdapter(private val songs : ArrayList<Song>) : RecyclerView.Adapter<PlayedSongsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.played_song_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(songs[position])
    }

    override fun getItemCount(): Int {
        return songs.size
    }

    class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView)
    {

        fun bindItems(song : Song)
        {
            val name = itemView.findViewById<TextView>(R.id.recycler_song_name)
            val image = itemView.findViewById<ImageView>(R.id.recycler_image)

            name.text = song.name

                Picasso.get()
                        .load(song.album_url)
                        .into(image)


        }


    }

}