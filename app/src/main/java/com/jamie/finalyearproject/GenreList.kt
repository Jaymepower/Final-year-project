package com.jamie.finalyearproject

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class GenreList : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.genrelist_layout)


        val genres = arrayListOf<String>(*resources.getStringArray(R.array.genre_list))


        val recycler = findViewById<RecyclerView>(R.id.genre_recycler)

        val adapter = GenreListAdapter(genres)

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recycler.layoutManager = mLayoutManager

        recycler.adapter = adapter






    }






}