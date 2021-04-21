package com.jamie.finalyearproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CatalogAdapter (private val songs : ArrayList<CatalogSong>) : RecyclerView.Adapter<CatalogAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.catalog_list_item, parent, false)
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

        fun bindItems(song : CatalogSong)
        {
            val name = itemView.findViewById<TextView>(R.id.song_name)
            val album = itemView.findViewById<TextView>(R.id.song_album)
            val artist = itemView.findViewById<TextView>(R.id.song_artist)
            val image = itemView.findViewById<ImageView>(R.id.genre_image)
            val explicit = itemView.findViewById<Button>(R.id.explicit)

            if(song.explicit)
                explicit.visibility = View.VISIBLE
            else
                explicit.visibility = View.INVISIBLE

            name.text = song.name
            album.text = song.album
            artist.text = song.artist


            Picasso.get()
                    .load(song.album_url)
                    .into(image)

        }

    }


}