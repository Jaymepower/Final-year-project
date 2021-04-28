package com.jamie.finalyearproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.find

class HistoryAdapter(val gameLog : ArrayList<GameLog>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.history_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(gameLog[position])
    }

    override fun getItemCount(): Int {
        return gameLog.size
    }

    class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView)
    {

        fun bindItems(log : GameLog)
        {
            val genre = itemView.findViewById<TextView>(R.id.log_genre)
            val subgenre = itemView.findViewById<TextView>(R.id.log_subgenre)
            val sp = itemView.findViewById<TextView>(R.id.log_sp)
            val date = itemView.findViewById<TextView>(R.id.log_date)
            val reacts = itemView.findViewById<TextView>(R.id.log_reaction)
            val line = itemView.findViewById<TextView>(R.id.log_lines)
            val score = itemView.findViewById<TextView>(R.id.log_score)

            val genreImage = itemView.findViewById<ImageView>(R.id.genre_image)
            val winImage = itemView.findViewById<ImageView>(R.id.log_win)

            genre.text = log.genre
            subgenre.text = log.subgenre
            sp.text = log.songsPlayed.toString()
            date.text = log.date
            reacts.text = log.reactions.toString()
            line.text = log.lines.toString()
            score.text = log.score.toString()

            if (log.win)
            {
                winImage.setImageResource(R.drawable.ic_trophy)
            }

            when(log.genre)
            {
                "Pop" -> genreImage.setImageResource(R.drawable.purple_pop)
                "Rock" -> genreImage.setImageResource(R.drawable.purple_rock)
                "Country" ->genreImage.setImageResource(R.drawable.purple_country)
                "Rap" -> genreImage.setImageResource(R.drawable.purple_rap)
                "Electronic" -> genreImage.setImageResource(R.drawable.purple_elect)
                "Festive" -> genreImage.setImageResource(R.drawable.purple_festive)
            }


        }


    }





}