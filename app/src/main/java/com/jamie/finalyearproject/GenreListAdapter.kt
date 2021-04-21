package com.jamie.finalyearproject

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class GenreListAdapter(val genreList : ArrayList<String>) : RecyclerView.Adapter<GenreListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.genre_list_item, parent, false)
        v.setOnClickListener {
            val genre = v.findViewById<TextView>(R.id.song_name)

            Log.i("Recycler",v.context.javaClass.simpleName)

            if(v.context.javaClass.simpleName == "GenreList")
            {
                val i = Intent(v.context, SubGenreList::class.java )
                i.putExtra("subgenre",genre.text.toString())
                v.context.startActivity(i)
            }
            else
            {
                val i = Intent(v.context, Catalog::class.java )
                i.putExtra("subgenre",genre.text.toString())
                v.context.startActivity(i)
            }


        }
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(genreList[position])
    }

    override fun getItemCount(): Int {
       return genreList.size
    }



    class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView)
    {

        fun bindItems(name : String)
        {
           val title = itemView.findViewById<TextView>(R.id.song_name)
            val image = itemView.findViewById<ImageView>(R.id.genre_image)

            title.text = name

            when(name)
            {
                "Pop" -> image.setImageResource(R.drawable.ic_microphone)
                "Rock" -> image.setImageResource(R.drawable.ic_rock)
                "Country" -> image.setImageResource(R.drawable.ic_acoustic)
                "Rap" -> image.setImageResource(R.drawable.ic_rap)
                "Electronic" -> image.setImageResource(R.drawable.ic_keyboard)
                "Festive" -> image.setImageResource(R.drawable.ic_festive)
                else -> image.setImageResource(R.drawable.vinyl)
            }

        }


    }


}