package com.jamie.musicbingo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jamie.musicbingo.adapters.GenreListAdapter

class SubGenreList : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.subgenre_list_layout)

        val subGenre = intent.getStringExtra("subgenre")


        val list = ArrayList<String>()

        when(subGenre)
        {
            "Pop" -> list.addAll(resources.getStringArray(R.array.pop_sublist))
            "Rock" -> list.addAll(resources.getStringArray(R.array.rock_sublist))
            "Country" -> list.addAll(resources.getStringArray(R.array.country_sublist))
            "Rap" -> list.addAll(resources.getStringArray(R.array.rap_sublist))
            "Electronic" -> list.addAll(resources.getStringArray(R.array.elect_sublist))
            "Festive" -> list.addAll(resources.getStringArray(R.array.festive_sublist))
        }

        val recycler = findViewById<RecyclerView>(R.id.subgenre_recycler)

        val adapter = GenreListAdapter(list)

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recycler.layoutManager = mLayoutManager

        recycler.adapter = adapter
    }
}